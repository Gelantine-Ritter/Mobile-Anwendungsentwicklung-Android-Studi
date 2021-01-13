package com.example.travelquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.travelquest.database.util.ObjectHandler;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartQuestionA = (Button) findViewById(R.id.btn_start);
        btnStartQuestionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectHandler.INSTANCE.initDB(getApplicationContext());
                startActivity(new Intent(MainActivity.this, QuestionActivity.class));
            }
        });


    }
}