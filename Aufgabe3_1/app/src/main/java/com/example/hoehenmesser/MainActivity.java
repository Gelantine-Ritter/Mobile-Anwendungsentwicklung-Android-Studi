package com.example.hoehenmesser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SyncStatusObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    private TextView textPressure;
    private TextView textHeight;
    private TextView textSeek;

    private SensorManager sensorManager;
    private Sensor druckSensor;

    Button button;
    SeekBar seekBar;

    double sensorValue;
    double slideValue;

    boolean sensorChanged;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor sharedPreferencesEditor;

    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[]sensorValues = sensorEvent.values;
            if (!sensorChanged) sensorValue = sensorValues[0];
            textPressure.setText(String.format("Druck: %s", String.valueOf(sensorValue)));
            System.out.println("In SENSORCHANGED()");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("In ONDESTROY()");
        // SHARED PREF
        saveValue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shared Preference Initialization
        sharedPreferences = this.getSharedPreferences("storedValues", MODE_PRIVATE);

        sensorChanged = false;

        textPressure = (TextView) findViewById(R.id.textPressure);
        textHeight = (TextView)findViewById(R.id.textHeight);
        textSeek = (TextView)findViewById(R.id.textSeek);

        seekBar = (SeekBar)findViewById(R.id.seekBar);

        button = (Button)findViewById(R.id.button);

        sensorManager = (SensorManager) getSystemService((SENSOR_SERVICE));
        druckSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        // shared Preference - get the values and set seekbar accordingly
        setValue();
        System.out.println(slideValue);

        seekBar.setMin(-100);
        seekBar.setMax(100);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sensorChanged = true;
                double prog = seekBar.getProgress();
                slideValue = prog/10;
                textSeek.setText(String.valueOf(slideValue));
                sensorValue = 1013.25 + slideValue;
                textPressure.setText(String.format("Druck: %s", String.valueOf(sensorValue)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                double prog = seekBar.getProgress();
                slideValue = prog/10;
                textSeek.setText(String.valueOf(slideValue));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double prog = seekBar.getProgress();
                slideValue = prog/10;
                textSeek.setText(String.valueOf(slideValue));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(sensorValue + " | " + slideValue);
                double hoehe = (288.15 / 0.0065 ) * ( 1 - Math.pow((sensorValue/1013.25), 1.0/5.255));
                textHeight.setText(String.format("Höhe: %s", (String.valueOf(hoehe)).toString()));
            }
        });
    }

    void setValue () {
        // SHARED PREFERENCE
        slideValue = sharedPreferences.getFloat("slideVal", 3.125f);
        System.out.println("did set Value of slider to " + slideValue);
    }

    void saveValue () {
        System.out.println("in saveValue() -- sensorValue saved = " + sensorValue);
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putFloat("slideVal",(float)sensorValue);
        sharedPreferencesEditor.apply();
        System.out.println("saved Value");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // SHARED PREF
        saveValue();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, druckSensor, SensorManager.SENSOR_DELAY_UI);
        // SHARED PREF
        setValue();
    }
}