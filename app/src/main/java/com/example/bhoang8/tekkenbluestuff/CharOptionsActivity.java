package com.example.bhoang8.tekkenbluestuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

public class CharOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_options);
        Character currentChar = CharactersArrayList.global_arrayList.get(CharactersArrayList.get_arrayList_pos());
        final String character_name = get_present_name(currentChar.getCharName());
        final String character_moves = currentChar.getMoveList().toString();

        TextView moveList_textView = findViewById(R.id.moveList_opt);
        TextView char_name_textView = findViewById(R.id.char_name_charOpt);
        ImageView char_pic_imageView = findViewById(R.id.char_img_charOpt);
        int curr_char_img = getResources().getIdentifier(("prof_" + character_name).toLowerCase(), "drawable", this.getPackageName());

        char_pic_imageView.setImageResource(curr_char_img);
        char_name_textView.setText(character_name);
        moveList_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveListIntent = new Intent(CharOptionsActivity.this, MoveListActivity.class);
                moveListIntent.putExtra("CHARACTER_NAME", character_name);
                moveListIntent.putExtra("CHARACTER_MOVES", character_moves);
                startActivity(moveListIntent);
            }
        });
    }

    private String get_present_name(String rawName){
        switch(rawName){
            case "devil-jin":
                return "Devil_Jin";

            case "lucky-chloe":
                return "Lucky_Chloe";

            case "master-raven":
                return "Master_Raven";

            default:
                return rawName.substring(0,1).toUpperCase() + rawName.substring(1);
        }
    }
}

        /*
        @Override
        public void onSaveInstanceState(Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString("CHARACTER_NAME", character_name);
            savedInstanceState.putString("CHARACTER_MOVES", character_moves);

        }

        @Override
        public void onRestoreInstanceState(Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            character_name = savedInstanceState.getString("CHARACTER_NAME");
            character_moves = savedInstanceState.getString("CHARACTER_MOVES");
        }
    }
*/
    /*
            if(getIntent().hasExtra("CHARACTER_NAME") && getIntent().hasExtra("CHARACTER_MOVES")){

            if(savedInstanceState != null){
                character_name = savedInstanceState.getString("CHARACTER_NAME");
                character_moves = savedInstanceState.getString("CHARACTER_MOVES");
            }
            else{
                character_name = getIntent().getExtras().getString("CHARACTER_NAME");
                character_moves = getIntent().getExtras().getString("CHARACTER_MOVES");
            }
            */
