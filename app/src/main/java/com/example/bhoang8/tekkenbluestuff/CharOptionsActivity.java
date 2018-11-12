package com.example.bhoang8.tekkenbluestuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

public class CharOptionsActivity extends AppCompatActivity {

    TextView moveList_textView;
    TextView keyMoves_textView;
    TextView char_name_textView;
    ImageView char_pic_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_options);
        Character currentChar = CharactersArrayList.global_arrayList.get(CharactersArrayList.get_arrayList_pos());
        final String character_name = currentChar.getCharName();
        final ArrayList<String[]> character_moves = currentChar.getMoveList();

        init_textViews();
        set_char_display(character_name);

        //On click, go to the movelist of the character
        moveList_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveListIntent = new Intent(CharOptionsActivity.this, MoveListActivity.class);
                moveListIntent.putExtra("CHARACTER_NAME", character_name);
                moveListIntent.putExtra("CHARACTER_MOVES", character_moves);
                startActivity(moveListIntent);
            }
        });

        keyMoves_textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent keyMovesIntent = new Intent(CharOptionsActivity.this, KeyMovesActivity.class);
                keyMovesIntent.putExtra("CHARACTER_NAME", character_name);
                keyMovesIntent.putExtra("CHARACTER_MOVES", character_moves);
                startActivity(keyMovesIntent);
            }
        });
    }

    //Get resource ID of current character
    private int get_char_img_id(String char_name){
        return getResources().getIdentifier(("prof_" + char_name).toLowerCase(),
                "drawable", this.getPackageName());
    }

    private void init_textViews(){
        moveList_textView = findViewById(R.id.moveList_opt);
        keyMoves_textView = findViewById(R.id.keyMoves_opt);
        char_name_textView = findViewById(R.id.char_name_charOpt);
        char_pic_imageView = findViewById(R.id.char_img_charOpt);
    }

    //Set imageView and textView for selected character
    private void set_char_display(String char_name){
        set_name_textView(char_name);
        int curr_char_img = get_char_img_id(char_name);
        char_pic_imageView.setImageResource(curr_char_img);

    }

    private void set_name_textView(String char_name){
        switch(char_name){
            case "CHLOE":
                char_name_textView.setText("LUCKY CHLOE");
                break;
            case "DEVILJIN":
                char_name_textView.setText("DEVIL JIN");
                break;
            case "RAVEN":
                char_name_textView.setText("MASTER RAVEN");
                break;
            default:
                char_name_textView.setText(char_name);
        }

    }
}
