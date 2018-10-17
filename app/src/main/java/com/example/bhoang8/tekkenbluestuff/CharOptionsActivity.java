package com.example.bhoang8.tekkenbluestuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class CharOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_options);

        Character currentChar = CharactersArrayList.global_arrayList.get(CharactersArrayList.get_arrayList_pos());
        final String character_name = currentChar.getCharName();
        final String character_moves = currentChar.getMoveList().toString();

        TextView moveList_textView = (TextView) findViewById(R.id.moveList_opt);
        TextView char_name_textView = (TextView) findViewById(R.id.char_name_charOpt);


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
