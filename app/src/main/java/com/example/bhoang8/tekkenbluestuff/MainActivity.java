package com.example.bhoang8.tekkenbluestuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView charTextView = findViewById(R.id.char_main_act);
        TextView guideTextView = findViewById(R.id.guide_main_act);

        charTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent charListIntent = new Intent(MainActivity.this, CharListActivity.class);
                startActivity(charListIntent);
            }
        });

    }
}
