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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_options);
        Character currentChar = CharactersArrayList.global_arrayList.get(CharactersArrayList.get_arrayList_pos());
        final String character_name = currentChar.getCharName();
        final ArrayList<String[]> character_moves = currentChar.getMoveList();

        TextView moveList_textView = findViewById(R.id.moveList_opt);
        TextView char_name_textView = findViewById(R.id.char_name_charOpt);
        ImageView char_pic_imageView = findViewById(R.id.char_img_charOpt);

        //Get resource ID of profile image for character
        int curr_char_img = get_char_img_id(character_name);

        char_pic_imageView.setImageResource(curr_char_img);
        char_name_textView.setText(character_name);

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
    }

    //Get resource ID of current character
    private int get_char_img_id(String char_name){
        return getResources().getIdentifier(("prof_" + char_name).toLowerCase(),
                "drawable", this.getPackageName());
    }

    //Account for cases where character name has a space
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
