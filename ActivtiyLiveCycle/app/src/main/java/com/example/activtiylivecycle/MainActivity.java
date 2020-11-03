package com.example.activtiylivecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.d("MainActivity", "onStart: App has started!");
        Toast.makeText(getApplicationContext(), "App has started!", Toast.LENGTH_LONG).show();
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("MainActivity", "onResume: App has resumed!");
        Toast.makeText(getApplicationContext(), "App has resumed!", Toast.LENGTH_LONG).show();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("MainActivity", "onPause: App has paused!");
        Toast.makeText(getApplicationContext(), "App has paused!", Toast.LENGTH_LONG).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("MainActivity", "onStop: App has stopped!");
        Toast.makeText(getApplicationContext(), "App has stopped!", Toast.LENGTH_LONG).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "onDestroy: App was destroyed!");
        Toast.makeText(getApplicationContext(), "App was destroyed!", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}