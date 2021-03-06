package com.example.travelquest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.travelquest.database.util.ObjectHandler;

/**
 * Startscreen und Initialisierung
 */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button btnStartQuestionA = findViewById(R.id.btn_start);
        btnStartQuestionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectHandler.INSTANCE.initDB(getApplicationContext());
                startActivity(new Intent(MainActivity.this, QuestionActivity.class));
            }
        });


    }
}