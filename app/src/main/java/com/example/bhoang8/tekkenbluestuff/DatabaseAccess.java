package com.example.bhoang8.tekkenbluestuff;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    //private constructor to avoid object creation from outside classes
    private DatabaseAccess(Context context, String db_name){
        this.openHelper = new DatabaseOpenHelper(context, db_name);
    }

    //Return singleton instance of database access
    public static DatabaseAccess getInstance(Context context, String db_name){
        if(instance == null){
            instance = new DatabaseAccess(context, db_name);
        }
        return instance;
    }

    //Open db connection
    public void open(){
        this.database = openHelper.getWritableDatabase();
    }

    //Close db connection
    public void close(){
        if(database != null){
            this.database.close();
        }
    }

    //Kill the instance of the database. For these purposes, the database will not need to be
    //reopened.
    public void killInstance(){
        if(instance != null){
            instance = null;
        }
    }

    //Get character name from table
    public String get_character_name(){
        Cursor cursor = database.rawQuery("SELECT * from name", null);
        cursor.moveToFirst();
        String character_name = cursor.getString(0);
        cursor.close();
        return character_name;
    }

    public long get_num_rows(){
        long count = DatabaseUtils.queryNumEntries(database, "moves");
        return count;
    }

    //Fill list with moves from current character
    public ArrayList<String[]> get_character_moves(){
        ArrayList<String[]> move_list = new ArrayList<>();


        //Want to convert this into a for loop such that each row is added in order of its
        //id in the moveList

            //Get all rows from moves
            Cursor cursor = database.rawQuery("SELECT * FROM moves", null);
            cursor.moveToFirst();

            //Add moves + their properties to an arraylist of string arrays
            while(!cursor.isAfterLast()){
                String[] move = new String[7];
                move[0] = Integer.toString(cursor.getInt(1)); //number in in-game movelist
                move[1] = cursor.getString(2); //command
                move[2] = cursor.getString(3); //hit height
                move[3] = cursor.getString(4); //start frame
                move[4] = cursor.getString(5); //block frame
                move[5] = cursor.getString(6); //hit frame
                move[6] = cursor.getString(7); //damage

                move_list.add(move);
                cursor.moveToNext();

        }

        cursor.close();

        return move_list;
    }
}

        //Need to figure out why SELECT WHERE query is not working
        /*
        long num_rows = get_num_rows();
        String db_query = "SELECT movelist_id, command from moves";
        String[] COLUMNS = {"movelist_id", "command", "hit_height", "start_frame", "block_frame",
                            "hit_frame", "damage"};

        for(int i = 0; i < num_rows; i++){
            String[] current_move_id = {Integer.toString(i + 1)};
            int current_int = i + 1;

            //Get row w/ move
            //Cursor cursor = database.query("moves", COLUMNS, "movelist_id=?",
            //        current_move_id, null,null,null, "1");


*/
