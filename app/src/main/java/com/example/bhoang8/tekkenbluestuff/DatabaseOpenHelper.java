package com.example.bhoang8.tekkenbluestuff;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context,String db_name){
        super(context, db_name, null, DATABASE_VERSION);
    }

}
