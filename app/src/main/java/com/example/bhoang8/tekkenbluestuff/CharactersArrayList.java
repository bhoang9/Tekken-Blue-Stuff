package com.example.bhoang8.tekkenbluestuff;

import java.util.ArrayList;

public class CharactersArrayList {

    public static ArrayList<Character> global_arrayList;
    private static int global_arrayList_position;

    public static int get_arrayList_pos(){
        return global_arrayList_position;
    }

    public static void setGlobal_arrayList_pos(int newPos){
        global_arrayList_position = newPos;
    }
}
