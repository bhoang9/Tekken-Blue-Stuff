package com.example.bhoang8.tekkenbluestuff;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class KeyMovesActivity extends Activity {

    ExpandableListAdapter keyMoveAdapter;
    ExpandableListView keyMoveListView;
    TextView nametextView;
    List<String> list_headers;
    HashMap<String, ArrayList<Move>> key_moves;
    String character_name;
    GifImageView move_gif;
    ArrayList<?> character_moves;
    LinearLayout lin_layout_keymoves;
    PopupWindow move_properties_popup;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keymoves_activity);

        nametextView = findViewById(R.id.char_name_keymoves);
        keyMoveListView = findViewById(R.id.key_moves_listview);
        lin_layout_keymoves = findViewById(R.id.lin_layout_keymoves);

        character_moves = (ArrayList<?>) getIntent().getExtras()
                .getSerializable("CHARACTER_MOVES");

        character_name = getIntent().getExtras().getString("CHARACTER_NAME");

        filter_movelist();

        keyMoveAdapter = new KeyMoveAdapter(this, list_headers, key_moves);

        nametextView.setText(character_name);
        keyMoveListView.setAdapter(keyMoveAdapter);
        set_child_click();

    }

    private void set_child_click(){
        keyMoveListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().
                        getSystemService(LAYOUT_INFLATER_SERVICE);

                View move_properties_popout = inflater.inflate(R.layout.move_properties_popout,null);

                move_properties_popup = new PopupWindow(move_properties_popout,
                        ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                if(Build.VERSION.SDK_INT >= 21){
                    move_properties_popup.setElevation(5.0f);
                }

                //Get the filtered list with the header of the group position
                ArrayList<Move> filtered_moves = key_moves.get(list_headers.get(groupPosition));

                TextView[] move_property_fields = MoveListActivity.get_move_property_fields(move_properties_popout);
                MoveListActivity.set_move_property_fields(move_property_fields, filtered_moves.get(childPosition));

                move_gif = move_properties_popout.findViewById(R.id.char_gif_charOpt);

                String current_move = MoveListActivity.get_current_move_filename(character_name.toLowerCase(),
                        Integer.parseInt(filtered_moves.get(childPosition).getMoveList_id()));

                int current_move_gif = getResources().getIdentifier(current_move, "drawable",
                        getApplicationContext().getPackageName());
                move_gif.setImageResource(current_move_gif);

                set_popup_properties();

                return true;
            }
        });
    }

    private void filter_movelist(){
        list_headers = new ArrayList<>();
        key_moves = new HashMap<>();
        ArrayList<Move> list_10f = new ArrayList<>();
        ArrayList<Move> list_12f = new ArrayList<>();
        ArrayList<Move> list_14f = new ArrayList<>();
        ArrayList<Move> list_15f = new ArrayList<>();
        ArrayList<Move> list_tailspin = new ArrayList<>();

        list_headers.add("10F");
        list_headers.add("12F");
        list_headers.add("14F");
        list_headers.add("15F");
        list_headers.add("Tailspin");

        for(int i = 0; i < character_moves.size(); i++){
           String[] curr_move = (String[]) character_moves.get(i);

           //Check exists b/c of completely empty columns in the table are counted as null
            //Will circumvent this in a better way in the future

           if(curr_move[3] == null){
               continue;
           }

           String start_frame = curr_move[3].replaceAll("\\s","");
           //String hit_height = curr_move[2].replaceAll("\\s", "");
            String hit_height = curr_move[2];

            if(start_frame.contains("10")){
                list_10f.add(new Move(curr_move[0],curr_move[1],curr_move[3],curr_move[4],
                        curr_move[5],curr_move[2],curr_move[6]));
            }
            else if(start_frame.contains("12")){
                list_12f.add(new Move(curr_move[0],curr_move[1],curr_move[3],curr_move[4],
                        curr_move[5],curr_move[2],curr_move[6]));
            }
            else if(start_frame.contains("14")){
                list_14f.add(new Move(curr_move[0],curr_move[1],curr_move[3],curr_move[4],
                        curr_move[5],curr_move[2],curr_move[6]));
            }
            else if(start_frame.contains("15")){
                list_15f.add(new Move(curr_move[0],curr_move[1],curr_move[3],curr_move[4],
                        curr_move[5],curr_move[2],curr_move[6]));
            }
            else if(hit_height.contains("SPIN")){
                list_tailspin.add(new Move(curr_move[0],curr_move[1],curr_move[3],curr_move[4],
                        curr_move[5],curr_move[2],curr_move[6]));
            }

        }

        key_moves.put(list_headers.get(0), list_10f);
        key_moves.put(list_headers.get(1), list_12f);
        key_moves.put(list_headers.get(2), list_14f);
        key_moves.put(list_headers.get(3), list_15f);
        key_moves.put(list_headers.get(4), list_tailspin);

    }

    private void set_popup_properties(){
        move_properties_popup.setOutsideTouchable(true);
        move_properties_popup.setFocusable(true);
        move_properties_popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        move_properties_popup.showAtLocation(lin_layout_keymoves, Gravity.CENTER,0,0);
    }
}
