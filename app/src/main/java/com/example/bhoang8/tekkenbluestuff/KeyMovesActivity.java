package com.example.bhoang8.tekkenbluestuff;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyMovesActivity extends Activity {

    ExpandableListAdapter keyMoveAdapter;
    ExpandableListView keyMoveListView;
    List<String> list_headers;
    HashMap<String, ArrayList<Move>> key_moves;
    String character_name;
    ArrayList<?> character_moves;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keymoves_activity);


        //get list view
        keyMoveListView = (ExpandableListView) findViewById(R.id.key_moves_listview);

        character_moves = (ArrayList<?>) getIntent().getExtras()
                .getSerializable("CHARACTER_MOVES");

        character_name = getIntent().getExtras().getString("CHARACTER_NAME");

        filter_movelist();

        keyMoveAdapter = new KeyMoveAdapter(this, list_headers, key_moves);

        keyMoveListView.setAdapter(keyMoveAdapter);
    }

    private void filter_movelist(){
        list_headers = new ArrayList<>();
        key_moves = new HashMap<>();
        ArrayList<Move> list_10f = new ArrayList<>();
        ArrayList<Move> list_12f = new ArrayList<>();
        ArrayList<Move> list_14f = new ArrayList<>();
        ArrayList<Move> list_15f = new ArrayList<>();

        list_headers.add("10F");
        list_headers.add("12F");
        list_headers.add("14F");
        list_headers.add("15F");

        for(int i = 0; i < character_moves.size(); i++){
           String[] curr_move = (String[])character_moves.get(i);
           String start_frame = curr_move[3].replaceAll("\\s","");


            if(start_frame.contains("10")){
                list_10f.add(new Move(curr_move[0],curr_move[1],curr_move[2],curr_move[3],
                        curr_move[4],curr_move[5],curr_move[6]));
            }
            else if(start_frame.contains("12")){
                list_12f.add(new Move(curr_move[0],curr_move[1],curr_move[2],curr_move[3],
                        curr_move[4],curr_move[5],curr_move[6]));
            }
            else if(start_frame.contains("14")){
                list_14f.add(new Move(curr_move[0],curr_move[1],curr_move[2],curr_move[3],
                        curr_move[4],curr_move[5],curr_move[6]));
            }
            else if(start_frame.contains("15")){
                list_15f.add(new Move(curr_move[0],curr_move[1],curr_move[2],curr_move[3],
                        curr_move[4],curr_move[5],curr_move[6]));
            }

        }

        key_moves.put(list_headers.get(0), list_10f);
        key_moves.put(list_headers.get(1), list_12f);
        key_moves.put(list_headers.get(2), list_14f);
        key_moves.put(list_headers.get(3), list_15f);

    }

}
