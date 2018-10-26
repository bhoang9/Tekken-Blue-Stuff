package com.example.bhoang8.tekkenbluestuff;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Character {

    private String charName;
    //private JSONArray moveList;
    private ArrayList<String[]> moveList;

    /*
    public Character(String nCharName, JSONArray moves){
        charName = nCharName;
        moveList = moves;
    }
*/
    public Character(String nCharName, ArrayList<String[]> moves){
        charName = nCharName;
        moveList = moves;
    }
    public String getCharName(){
        return charName;
    }

    public ArrayList<String[]> getMoveList(){
        return moveList;
    }

    /*
    public JSONArray getMoveList(){
        return moveList;
    }
    */
}
