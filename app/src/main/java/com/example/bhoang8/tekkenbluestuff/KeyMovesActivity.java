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
    ArrayList<?> character_moves;
    LinearLayout lin_layout_keymoves;
    MovePopout move_popout;

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
                move_popout = MovePopout.getInstance(getApplicationContext());
                move_popout.set_filtered_moves(key_moves.get(list_headers.get(groupPosition)));
                move_popout.set_move_property_fields(move_popout.get_move(childPosition));
                move_popout.set_move_gif(move_popout.get_current_move_gif(character_name, childPosition));
                move_popout.set_popup_properties(lin_layout_keymoves);
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
        ArrayList<Move> list_throw = new ArrayList<>();

        list_headers.add("10F");
        list_headers.add("12F");
        list_headers.add("14F");
        list_headers.add("15F");
        list_headers.add("Tailspin");
        list_headers.add("Throw");

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
                list_10f.add(new_move(curr_move));
            }
            else if(start_frame.contains("12")){
                list_12f.add(new_move(curr_move));
            }
            else if(start_frame.contains("14")){
                list_14f.add(new_move(curr_move));
            }
            else if(start_frame.contains("15")){
                list_15f.add(new_move(curr_move));
            }

            if(hit_height.contains("SPIN")){
                list_tailspin.add(new_move(curr_move));
            }
            if(hit_height.contains("THROW")){
                list_throw.add(new_move(curr_move));
            }

        }

        key_moves.put(list_headers.get(0), list_10f);
        key_moves.put(list_headers.get(1), list_12f);
        key_moves.put(list_headers.get(2), list_14f);
        key_moves.put(list_headers.get(3), list_15f);
        key_moves.put(list_headers.get(4), list_tailspin);
        key_moves.put(list_headers.get(5), list_throw);

    }

    private Move new_move(String[] curr_move){
        Move new_move = new Move(curr_move[0],curr_move[1],curr_move[3],curr_move[4],
                curr_move[5],curr_move[2],curr_move[6]);
        return new_move;
    }
}
