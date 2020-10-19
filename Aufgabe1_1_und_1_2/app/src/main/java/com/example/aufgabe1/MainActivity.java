package com.example.aufgabe1;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView option1 = (TextView)findViewById(R.id.option1);
        TextView option2 = (TextView)findViewById(R.id.option2);
        TextView option3 = (TextView)findViewById(R.id.option3);
        TextView option4 = (TextView)findViewById(R.id.option4);



        //Console Ausgabe bei Click auf die jeweilige Option
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Option 1 geklickt");

            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Option 2 geklickt");

            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Option 3 geklickt");

            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Option 4 geklickt");

            }
        });
    }

}