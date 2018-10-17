package com.example.bhoang8.tekkenbluestuff;

import org.json.JSONArray;

public class Character {

    private String charName;
    private JSONArray moveList;

    public Character(String nCharName, JSONArray moves){
        charName = nCharName;
        moveList = moves;
    }

    public String getCharName(){
        return charName;
    }

    public JSONArray getMoveList(){
        return moveList;
    }
}
