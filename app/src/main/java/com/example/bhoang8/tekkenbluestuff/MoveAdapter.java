package com.example.bhoang8.tekkenbluestuff;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MoveAdapter extends ArrayAdapter<Move> {

    public MoveAdapter(Activity context, ArrayList<Move> moves) {
        super(context, 0, moves);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.move_list_item, parent, false);
        }

        Move currentMove = getItem(position);
        TextView move_command = (TextView) listItemView.findViewById(R.id.move_command);


        move_command.setText(currentMove.getCommand());

        return listItemView;
    }

}