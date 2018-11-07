package com.example.bhoang8.tekkenbluestuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyMoveAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> keyMove_headers;
    //private ArrayList<Move> keyMoves;
    private HashMap<String, ArrayList<Move>> keyMoves;

    public KeyMoveAdapter(Context nContext, List<String> nKeyMove_headers,
                          HashMap<String, ArrayList<Move>> nKeyMoves){
        context = nContext;
        keyMove_headers = nKeyMove_headers;
        keyMoves = nKeyMoves;
    }

    public Move getChild(int groupPosition, int childPosition){
        return keyMoves.get(keyMove_headers.get(groupPosition)).get(childPosition);
    }

    public int getChildrenCount(int groupPosition){
        return keyMoves.get(keyMove_headers.get(groupPosition)).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }

    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent){
        final String childText = getChild(groupPosition, childPosition).getCommand();

        if(convertView == null){
            LayoutInflater infalInfalter =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInfalter.inflate(R.layout.keymoves_child_item, null);
        }

        TextView childTextView = convertView.findViewById(R.id.keymove_child_txt);
        childTextView.setText(childText);

        return convertView;
    }

    public String getGroup(int groupPosition){
        return keyMove_headers.get(groupPosition);
    }

    public int getGroupCount(){
        return keyMove_headers.size();
    }

    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        String headerTitle = getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater infalInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.keymoves_group_item, null);
        }

        TextView group_header = (TextView) convertView.findViewById(R.id.keymove_group_txt);
        group_header.setText(headerTitle);

        return convertView;
    }

    public boolean hasStableIds(){
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition){
        return true;
    }
}
