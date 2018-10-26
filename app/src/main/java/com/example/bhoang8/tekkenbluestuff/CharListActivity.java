package com.example.bhoang8.tekkenbluestuff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CharListActivity extends AppCompatActivity {
    /*
    String[] character_array = {"akuma","alisa", "asuka", "bob", "bryan", "claudio", "devil-jin",
            "dragunov", "eddy","eliza","feng","gigas","heihachi",
            "hwoarang","jack7","jin","josie","katarina","kazuya",
            "king","kuma","lars","lei","law","lee","leo","lili",
            "lucky-chloe","master-raven","miguel","nina","noctis",
            "paul","shaheen","steve","xiaoyu","yoshimitsu"};
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characterlist_activity);

        //Arraylist only created if one does not already exist
        if(CharactersArrayList.global_arrayList == null) {
            CharactersArrayList.global_arrayList = new ArrayList<Character>();


            //Add all characters to arrayList when arrayList not yet assigned
            /*
            for(int i = 0; i < character_array.length; i++) {
                String fileName = character_array[i] + "_frame_data";
                JSONObject character_JSONObj = getJSON_obj(fileName);
                add_character(character_JSONObj);
            }
            */

            //TODO: Have app pull data from SQL databases instead
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this, "JOSIE_frame_data.db");
            databaseAccess.open();
            String character_name = databaseAccess.get_character_name();
            ArrayList<String[]> character_moves = databaseAccess.get_character_moves();
            CharactersArrayList.global_arrayList.add(new Character(character_name, character_moves));

        }
        //Define character adapter for listview and init listView var
        CharacterAdapter characterAdapter = new CharacterAdapter(this,
                CharactersArrayList.global_arrayList);

        ListView listView = (ListView) findViewById(R.id.character_list);
        listView.setAdapter(characterAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent character_options_intent = new Intent(CharListActivity.this, CharOptionsActivity.class);
                CharactersArrayList.setGlobal_arrayList_pos(i);
                startActivity(character_options_intent);
            }
        });
    }

    //get JSON object containing character data
    private JSONObject getJSON_obj(String fileName){
        JSONObject nJsonObj = null;
        String nJsonString;
        try{
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            nJsonString = new String(buffer, "UTF-8");
            try {
                nJsonObj = new JSONObject(nJsonString);
            } catch (JSONException e){
                e.printStackTrace();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return nJsonObj;
    }

    //TODO: add_character function in JSON implementation
    //add character to arrayList of characters
    /*
    private void add_character(JSONObject nCharacter){
        String char_name;
        JSONArray char_moves;
        try{
            char_name = nCharacter.getString("name");
            char_moves = nCharacter.getJSONArray("moves");
            //Keeping this global here, not sure
            CharactersArrayList.global_arrayList.add(new Character(char_name, char_moves));
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
    */

}
