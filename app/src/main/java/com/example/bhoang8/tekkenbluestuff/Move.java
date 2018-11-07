package com.example.bhoang8.tekkenbluestuff;

import org.json.JSONArray;
import org.json.JSONObject;

public class Move {

    private String movelist_id;
    private String command;
    private String start_frame;
    private String block_frame;
    private String hit_frame;
    private String hit_height;
    private String damage;

        public Move(String nMoveList_id, String nCommand, String nStart_frame, String nBlock_frame,
                    String nHit_frame, String nHit_height, String nDamage){

            movelist_id = nMoveList_id;
            command = nCommand;
            start_frame = nStart_frame;
            block_frame = nBlock_frame;
            hit_frame = nHit_frame;
            hit_height = nHit_height;
            damage = nDamage;
        }

        public String getCommand(){
            return command;
        }

        public String getStart_frame(){
            return start_frame;
        }

        public String getBlock_frame(){
            return block_frame;
        }

        public String getHit_frame(){
            return hit_frame;
        }

        public String getHit_height(){
            return hit_height;
        }

        public String getDamage(){
            return damage;
        }

        public String getMoveList_id() { return movelist_id; }
}
