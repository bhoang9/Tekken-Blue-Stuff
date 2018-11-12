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

     ArrayList<?> character_moves = null;
     String character_name = null;
     TextView char_name;
     ListView movelist_listView;
     ArrayList<Move> move_list = null;
     MoveAdapter moveAdapter;
     LinearLayout lin_layout_movelist;
     SearchView searchView;
     MovePopout move_popout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movelist_activity);

        if(verify_extras()){
            character_moves = (ArrayList<?>) getIntent().getExtras()
                    .getSerializable("CHARACTER_MOVES");

            character_name = getIntent().getExtras().getString("CHARACTER_NAME");
        } else{
            character_moves = null;
            character_name = null;
        }

        lin_layout_movelist = findViewById(R.id.lin_layout_moveList);
        char_name = findViewById(R.id.char_name_moveList);
        movelist_listView = findViewById(R.id.move_list);
        searchView = findViewById(R.id.moveList_search);

        set_searchview_properties();

        char_name.setText(character_name);
        //populate arraylist w/ character's moves
        move_list = fill_movelist(character_moves);

        moveAdapter = new MoveAdapter(this, move_list);
        movelist_listView.setAdapter(moveAdapter);

        //onClick, display move properties in a popout window
        set_item_click();

    }

    private void set_item_click(){
        movelist_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                move_popout = MovePopout.getInstance(getApplicationContext());
                move_popout.set_filtered_moves(moveAdapter.getFiltered_moves());
                move_popout.set_move_property_fields(move_popout.get_move(position));
                move_popout.set_move_gif(move_popout.get_current_move_gif(character_name, position));
                move_popout.set_popup_properties(lin_layout_movelist);

            }
        });

    }

    private void set_searchview_properties(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String search_command) {
                moveAdapter.getFilter().filter(search_command);
                return true;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                searchView.setIconified(false);
            }
        });

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






}
