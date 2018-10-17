package com.example.bhoang8.tekkenbluestuff;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class JSONUtils extends AppCompatActivity {

    //Get JSON object from JSON file in assets
    public JSONObject get_JSON(String JSON_file_name) {
        String JSON_string = null;
        JSONObject returnJSON = null;
        try {
            InputStream is = getAssets().open(JSON_file_name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            JSON_string = new String(buffer, "UTF-8");
            try {
                returnJSON = new JSONObject(JSON_string);
            } catch (JSONException e){
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnJSON;
    }
}
