package com.example.bhoang8.tekkenbluestuff;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MoveListActivity extends AppCompatActivity {
    JSONArray char_moves_JSON = null;
    private PopupWindow move_properties_popup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movelist_activity);
        final ArrayList<?> character_moves;
        final String character_name;

        /*
        if(verify_extras()){
            character_moves = (ArrayList<?>) getIntent().getExtras()
                    .getSerializable("CHARACTER_MOVES");

            character_name = getIntent().getExtras().getString("CHARACTER_NAME");
        } else{
            character_moves = null;
            character_name = null;
        }
*/
        character_moves = (ArrayList<?>) getIntent().getExtras()
                .getSerializable("CHARACTER_MOVES");

        character_name = getIntent().getExtras().getString("CHARACTER_NAME");
        final LinearLayout lin_layout_movelist = (LinearLayout) findViewById(R.id.lin_layout_moveList);
        TextView char_name = (TextView) findViewById(R.id.char_name_moveList);
        ListView listView = (ListView) findViewById(R.id.move_list);
        final ArrayList<Move> move_list = new ArrayList<Move>();

        //need to populate arraylist w/ moves
        try {
            for (int i = 0; i < character_moves.size(); i++) {
                String[] currentMove = (String[])character_moves.get(i);
                String movelist_id = currentMove[0];
                String command = currentMove[1];
                String hit_height = currentMove[2];
                String start_frame = currentMove[3];
                String block_frame = currentMove[4];
                String hit_frame = currentMove[5];
                String damage = currentMove[6];

                move_list.add(new Move(movelist_id, command, start_frame, block_frame,
                        hit_frame, hit_height, damage));
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        }
        /*
        try {
            char_moves_JSON = new JSONArray(character_moves);
            for (int i = 0; i < char_moves_JSON.length(); i++) {
                JSONObject currentMove = char_moves_JSON.getJSONObject(i);
                String command = currentMove.optString("command");
                String start_frame = currentMove.optString("start_frame");
                String block_frame = currentMove.optString("block_frame");
                String hit_frame = currentMove.optString("hit_frame");
                String CH_frame = currentMove.optString("CH_frame");
                String hit_height = currentMove.optString("hit_height");
                String damage = currentMove.optString("damage");
                String notes = currentMove.optString("notes");
                move_list.add(new Move(command, start_frame, block_frame, hit_frame, CH_frame,
                        hit_height,damage, notes));
                Log.d("move_list addition", command);
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
        */

        char_name.setText(character_name);
        MoveAdapter moveAdapter = new MoveAdapter(this, move_list);
        listView.setAdapter(moveAdapter);

        //onClick, display move properties in a popout window
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().
                        getSystemService(LAYOUT_INFLATER_SERVICE);

                View move_properties_popout = inflater.inflate(R.layout.move_properties_popout,null);
                move_properties_popup = new PopupWindow(move_properties_popout, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                if(Build.VERSION.SDK_INT >= 21){
                    move_properties_popup.setElevation(5.0f);
                }

                //popup window closes on clicking outside
                move_properties_popup.setOutsideTouchable(true);
                move_properties_popup.setFocusable(true);
                move_properties_popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                String current_move = get_current_move_filename(character_name, i);
                TextView move_property_command =  move_properties_popout.findViewById((R.id.property_move_command));
                TextView move_property_start_frame =  move_properties_popout.findViewById(R.id.property_start_frame);
                TextView move_property_block_frame =  move_properties_popout.findViewById(R.id.property_move_block_frame);
                TextView move_property_hit_frame =  move_properties_popout.findViewById(R.id.property_hit_frame);
                TextView move_property_CH_frame =  move_properties_popout.findViewById(R.id.property_CH_frame);
                TextView move_property_height =  move_properties_popout.findViewById(R.id.property_height);
                TextView move_property_damage =  move_properties_popout.findViewById(R.id.property_damage);
                TextView move_property_notes =  move_properties_popout.findViewById(R.id.property_notes);
                TextView move_notes_property =  move_properties_popout.findViewById(R.id.notes_property_string);
                GifImageView move_gif = move_properties_popout.findViewById(R.id.char_gif_charOpt);


                int current_move_gif = getResources().getIdentifier(current_move, "drawable",
                        getApplicationContext().getPackageName());
                move_gif.setImageResource(current_move_gif);
                //Set textviews to move properties
                move_property_command.setText(move_list.get(i).getCommand());
                move_property_start_frame.setText(move_list.get(i).getStart_frame());
                move_property_block_frame.setText(move_list.get(i).getBlock_frame());
                move_property_hit_frame.setText(move_list.get(i).getHit_frame());
                //move_property_CH_frame.setText(move_list.get(i).getCH_frame());
                move_property_height.setText(move_list.get(i).getHit_height());
                move_property_damage.setText(move_list.get(i).getDamage());

                //If the move does not have notes, hide the notes texview
                /*
                if(move_list.get(i).getNotes().equals("")) {
                    move_notes_property.setVisibility(View.GONE);
                    move_property_notes.setVisibility(View.GONE);
                }
                else{
                    move_property_notes.setText(move_list.get(i).getNotes());
                }
*/
                move_properties_popup.showAtLocation(lin_layout_movelist, Gravity.CENTER,0,0);
            }
        });


    }

    private String get_current_move_filename(String character_name, int position){
        String current_move_file = character_name.toLowerCase() + "_move_" + Integer.toString(position + 1);

        return current_move_file;
    }

    private Boolean verify_extras(){
        ArrayList<?> testArr = (ArrayList<?>) getIntent().getExtras()
                .getSerializable("CHARACTER_MOVES");
        String testName = getIntent().getExtras().getString("CHARACTER_NAME");

        if(testArr != null && testName != null){
            return true;
        }
        else{
            return false;
        }
    }

}
