package com.example.bhoang8.tekkenbluestuff;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
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
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movelist_activity);

        final ArrayList<?> character_moves;
        final String character_name;


        if(verify_extras()){
            character_moves = (ArrayList<?>) getIntent().getExtras()
                    .getSerializable("CHARACTER_MOVES");

            character_name = getIntent().getExtras().getString("CHARACTER_NAME");
        } else{
            character_moves = null;
            character_name = null;
        }

        final LinearLayout lin_layout_movelist = findViewById(R.id.lin_layout_moveList);
        TextView char_name = findViewById(R.id.char_name_moveList);
        ListView listView = findViewById(R.id.move_list);

        //populate arraylist w/ character's moves
        final ArrayList<Move> move_list = fill_movelist(character_moves);

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
                move_properties_popup = new PopupWindow(move_properties_popout,
                        ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                if(Build.VERSION.SDK_INT >= 21){
                    move_properties_popup.setElevation(5.0f);
                }

                //mPager = findViewById(R.id.pager);
                //mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

                //popup window closes on clicking outside
                move_properties_popup.setOutsideTouchable(true);
                move_properties_popup.setFocusable(true);
                move_properties_popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView[] move_property_fields = get_move_property_fields(move_properties_popout);
                TextView move_property_command =  move_property_fields[0];
                TextView move_property_start_frame =  move_property_fields[1];
                TextView move_property_block_frame =  move_property_fields[2];
                TextView move_property_hit_frame =  move_property_fields[3];
                TextView move_property_height =  move_property_fields[4];
                TextView move_property_damage =  move_property_fields[5];
                GifImageView move_gif = move_properties_popout.findViewById(R.id.char_gif_charOpt);

                //Get the gif associated w/ the current move being looked at
                String current_move = get_current_move_filename(character_name.toLowerCase(),
                        Integer.parseInt(move_list.get(i).getMoveList_id()));
                int current_move_gif = getResources().getIdentifier(current_move, "drawable",
                        getApplicationContext().getPackageName());
                move_gif.setImageResource(current_move_gif);

                //Set textviews to move properties
                move_property_command.setText(move_list.get(i).getCommand());
                move_property_start_frame.setText(move_list.get(i).getStart_frame());
                move_property_block_frame.setText(move_list.get(i).getBlock_frame());
                move_property_hit_frame.setText(move_list.get(i).getHit_frame());
                move_property_height.setText(move_list.get(i).getHit_height());
                move_property_damage.setText(move_list.get(i).getDamage());

                move_properties_popup.showAtLocation(lin_layout_movelist, Gravity.CENTER,0,0);
            }
        });


    }

    private String get_current_move_filename(String character_name, int position){
        String current_move_file = character_name.toLowerCase() + "_move_" + Integer.toString(position);

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

    private ArrayList<Move> fill_movelist(ArrayList<?> character_moves){
        ArrayList<Move> nMoveList = new ArrayList<>();
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

                nMoveList.add(new Move(movelist_id, command, start_frame, block_frame,
                        hit_frame, hit_height, damage));
            }
        } catch(NullPointerException e){
            e.printStackTrace();
            return null;
        }

        return nMoveList;
    }

    //Get the textView fields of the popout
    private TextView[] get_move_property_fields(View move_prop_popout){
        TextView[] retArr = new TextView[6];
        retArr[0] =  move_prop_popout.findViewById((R.id.property_move_command));
        retArr[1] =  move_prop_popout.findViewById(R.id.property_start_frame);
        retArr[2] =  move_prop_popout.findViewById(R.id.property_move_block_frame);
        retArr[3] =  move_prop_popout.findViewById(R.id.property_hit_frame);
        retArr[4] =  move_prop_popout.findViewById(R.id.property_height);
        retArr[5] =  move_prop_popout.findViewById(R.id.property_damage);

        return retArr;
    }

}
/*
    //Init options for searchable
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        //Associate searchable config w/ the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        return true;
    }
    */

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