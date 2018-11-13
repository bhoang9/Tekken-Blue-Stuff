package com.example.bhoang8.tekkenbluestuff;

import java.util.ArrayList;

public class CharactersArrayList {

    private static CharactersArrayList instance;
    private ArrayList<Character> global_arrayList;
    private int global_arrayList_position;

    private CharactersArrayList(){
        global_arrayList = new ArrayList<>();
    }

    public static CharactersArrayList getInstance(){
        if(instance == null){
            instance = new CharactersArrayList();
        }
        return instance;
    }

    public void add(Character newChar){
        global_arrayList.add(newChar);
    }

    public ArrayList<Character> get_global_arrayList(){
        return global_arrayList;
    }

    public Character get_current_character(){
        return global_arrayList.get(global_arrayList_position);
    }

    public int get_arrayList_pos(){
        return global_arrayList_position;
    }

    public void setGlobal_arrayList_pos(int newPos){
        global_arrayList_position = newPos;
    }
}
