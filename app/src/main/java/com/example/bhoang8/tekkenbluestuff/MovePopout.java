package com.example.bhoang8.tekkenbluestuff;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MovePopout extends Application {


    private static MovePopout instance;
    Context context;
    LayoutInflater inflater;
    View move_properties_popout;
    PopupWindow move_properties_popup;
    GifImageView move_gif;
    TextView[] move_property_fields;
    ArrayList<Move> filtered_moves;

    private MovePopout(Context nContext){
        context = nContext;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        move_properties_popout = inflater.inflate(R.layout.move_properties_popout,null);

        move_properties_popup = new PopupWindow(move_properties_popout,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        move_gif = move_properties_popout.findViewById(R.id.char_gif_charOpt);

        move_property_fields = get_move_property_fields(move_properties_popout);
    }

    public static MovePopout getInstance(Context context){
        if(instance == null){
            instance = new MovePopout(context);
        }

        return instance;
    }

    public void set_move_gif(int current_move_gif){
        move_gif.setImageResource(current_move_gif);
    }

    public void set_filtered_moves(ArrayList<Move> filtered_list){
        filtered_moves = filtered_list;
    }

    public void set_popup_properties(LinearLayout lin_layout){
        if(Build.VERSION.SDK_INT >= 21){
            move_properties_popup.setElevation(5.0f);
        }
        move_properties_popup.setOutsideTouchable(true);
        move_properties_popup.setFocusable(true);
        move_properties_popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        move_properties_popup.showAtLocation(lin_layout, Gravity.CENTER,0,0);
    }

    public void set_move_property_fields(Move nMove){
        move_property_fields[0].setText(nMove.getCommand());
        move_property_fields[1].setText(nMove.getStart_frame());
        move_property_fields[2].setText(nMove.getBlock_frame());
        move_property_fields[3].setText(nMove.getHit_frame());
        move_property_fields[4].setText(nMove.getHit_height());
        move_property_fields[5].setText(nMove.getDamage());
    }

    private TextView[] get_move_property_fields(View move_prop_popout){
        TextView[] retArr = new TextView[6];
        retArr[0] =  move_prop_popout.findViewById((R.id.property_move_command));
        retArr[1] =  move_prop_popout.findViewById(R.id.property_start_frame);
        retArr[2] =  move_prop_popout.findViewById(R.id.property_move_block_frame);
        retArr[3] =  move_prop_popout.findViewById(R.id.property_hit_frame);
        retArr[4] =  move_prop_popout.findViewById(R.id.property_height);
        retArr[5] =  move_prop_popout.findViewById(R.id.property_damage);

        return retArr;
    }

    public Move get_move(int position){
        return filtered_moves.get(position);
    }

    public int get_current_move_gif(String character_name, int position){
        String current_move = get_current_move_filename(character_name.toLowerCase(),
                Integer.parseInt(filtered_moves.get(position).getMoveList_id()));

        int current_move_gif = context.getResources().getIdentifier(current_move, "drawable",
                context.getPackageName());

        return current_move_gif;
    }

    private static String get_current_move_filename(String character_name, int position){
        String char_name;

        if(character_name.contains("jack")){
            char_name = "jack7";
        }
        else{
            char_name = character_name;
        }
        String current_move_file = char_name + "_move_" + Integer.toString(position);

        return current_move_file;
    }


}
