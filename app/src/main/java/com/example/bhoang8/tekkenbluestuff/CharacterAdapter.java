package com.example.bhoang8.tekkenbluestuff;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class CharacterAdapter extends ArrayAdapter<Character> {
    TextView character_name;

    public CharacterAdapter(Activity context, ArrayList<Character> characters){
        super(context, 0, characters);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.character_list_item, parent, false);
        }

        Character currentCharacter = getItem(position);
        character_name = listItemView.findViewById(R.id.char_name_listItem);
        set_char_name_listItem(currentCharacter.getCharName());

        return listItemView;
        }

    //Set name displayed as the list item. Checks for particular cases where name has space or dash
    private void set_char_name_listItem(String char_name){
        switch(char_name){
            case "CHLOE":
                character_name.setText("LUCKY CHLOE");
                break;
            case "DEVILJIN":
                character_name.setText("DEVIL JIN");
                break;
            case "RAVEN":
                character_name.setText("MASTER RAVEN");
                break;
            default:
                character_name.setText(char_name);
        }

    }
    }

