package com.example.bhoang8.tekkenbluestuff;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class MoveAdapter extends BaseAdapter implements Filterable {
    Context nContext;
    LayoutInflater inflater;
    private ArrayList<Move> moves = null;
    private ArrayList<Move> filtered_moves = null;
    private ItemFilter nFilter = new ItemFilter();

    public MoveAdapter(Activity context, ArrayList<Move> nMoves) {
        this.filtered_moves = nMoves;
        this.moves = nMoves;
        inflater = LayoutInflater.from(context);

    }

    public int getCount(){
        return filtered_moves.size();
    }

    public Object getItem(int position){
        return filtered_moves.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public ArrayList<Move> getFiltered_moves(){
        return filtered_moves;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        //Keeps references to children views, avoids having to call
        //findViewById() so often
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.move_list_item, null);

            holder = new ViewHolder();
            holder.move_command = convertView.findViewById(R.id.move_command);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //Set the current list item command
        holder.move_command.setText(filtered_moves.get(position).getCommand());

        return convertView;
    }

    static class ViewHolder{
        TextView move_command;
    }

    public Filter getFilter(){
        return nFilter;
    }

    private class ItemFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint){

            //The "constraint" is the filter. For example, we want all commands that contain
            //"1, 1, 2", so "1,1,2" is the constraint
            String filterString = constraint.toString().toLowerCase().replaceAll("\\s","");

            FilterResults results = new FilterResults();

            final ArrayList<Move> tMoves = moves;

            int count = tMoves.size();
            final ArrayList<Move> nMoves = new ArrayList<Move>(count);

            String filterableString;

            for(int i = 0; i < count; i++){
                //The command of the current move being looked at
                filterableString = tMoves.get(i).getCommand().replaceAll("\\s","");
                if(filterableString.toLowerCase().contains(filterString)){
                    nMoves.add(tMoves.get(i));
                }
            }

            results.values = nMoves;
            results.count = nMoves.size();

            return results;

        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
            filtered_moves = (ArrayList<Move>) results.values;
            notifyDataSetChanged();
        }
    }

}
