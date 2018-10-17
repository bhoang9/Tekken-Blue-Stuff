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
        TextView character_name = (TextView) listItemView.findViewById(R.id.char_name_listItem);

        character_name.setText(currentCharacter.getCharName());

        return listItemView;
        }
    }

