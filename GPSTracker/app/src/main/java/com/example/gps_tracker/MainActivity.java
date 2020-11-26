package com.example.gps_tracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;


import android.os.Environment;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LocationListener{
    private static final String TAG = "";


    boolean timerRunning;

    Context context;

    Button btn_startStop;
    Switch switch1;

    boolean startStop;

    File file;

    double longitude;
    double latitude;

    double hoehe;

    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    LocationManager locationManager;

    String timestampNow;

    // --------- Hoehenmesser ----------------

    private SensorManager sensorManager;
    private Sensor druckSensor;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float [] sensorValues = event.values;
            setHoehe((288.15 / 0.0065 ) * ( 1 - Math.pow((sensorValues[0]/1013.25), 1.0/5.255)));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    void setHoehe (double h){
        this.hoehe = h;
    }

    // ------------------------------------------------------

    List<String[]> toCSVList;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_startStop = findViewById(R.id.btn_StartStop);
        switch1 = findViewById(R.id.csvGpx);

        // --------------------------------------------------------

        init();
        startButtonActivity();

        // -------------------------------------------------------

        // timed logging activated
        timerRunning = true;
    }

    void init () {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        toCSVList = new ArrayList<String[]>();
        startStop = false;
        file = null;
    }

    void startButtonActivity() {
        btn_startStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!startStop) {
                    startStop = true;
                    btn_startStop.setText("Stop");
                    initLocationTracking();
                } else {
                    startStop = false;
                    btn_startStop.setText("Start");
                    generateCSV(prepareDataForCSVPrinting(toCSVList));
                    file = null;
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // timed logging deactivated
        timerRunning = false;
    }

    String prepareDataForCSVPrinting(List<String[]>list){
        // CSV Header first
        String toCSVString = "Time,Longitude,Latitude,Height\n";

        for (String[]arr : list) {
            toCSVString += arr[0] + ",";
            toCSVString += arr[1] + ",";
            toCSVString += arr[2] + ",";
            toCSVString += arr[3] + "\n";
        }
        return toCSVString;
    }

    // PREPARE DATA FOR PRINTING
    // GETS CALLED ALL THE TIME (VIA ONLOCATIONCHANGED)
    void logData() {

        this.timestampNow = String.valueOf(new Timestamp(System.currentTimeMillis()));

        String [] data = new String [4];

        data[0] = ""+ this.timestampNow;
        data[1] = ""+ this.longitude;
        data[2] = ""+ this.latitude;
        data[3] = "" + this.hoehe;

        toCSVList.add(data);
    }

    public void generateCSV(String content) {

        String filename = "tracked_data_" + timestampNow + ".csv";
        Log.d(TAG, content);

        File root = getExternalFilesDir ("CSV_STORE");
        if (!root.exists()) {
            root.mkdirs();
        }

        try {
            File csvfile = new File(root, filename);
            FileWriter writer = new FileWriter(csvfile);
            writer.append(content);
            writer.flush();
            writer.close();

            // THIS IS WHERE THE DATA HIDES
            // Log.d(TAG, "YHUHE ||| " + csvfile.getAbsolutePath() );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ------------ GPS ----------------------------

    // INIT LOCATION TRACKING
    void initLocationTracking () {
        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        getGPS();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        logData();
    }

    private void getGPSNetwork() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
// to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
    }

    //Case: GPS Daten über Satellit abrufen
    private void getGPS() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
    }
}