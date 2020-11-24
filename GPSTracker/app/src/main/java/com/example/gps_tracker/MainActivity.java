package com.example.gps_tracker;

import android.Manifest;
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

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    Location location;
    LocationManager locationManager;

    // --------- Hoehenmesser ----------------

    private SensorManager sensorManager;
    private Sensor druckSensor;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float [] sensorValues = event.values;
            hoehe = (288.15 / 0.0065 ) * ( 1 - Math.pow((sensorValues[0]/1013.25), 1.0/5.255));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    List<String[]> toCSVList;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "ICH STARTE JETZT");

        // --------------------------------------------------------

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        toCSVList = new ArrayList<String[]>();

        file = null;

        // false == läuft nicht
        // true == läuft
        startStop = false;


        Log.d(TAG, "Ich laufe immer noch.");

        btn_startStop = findViewById(R.id.btn_StartStop);
        switch1 = findViewById(R.id.csvGpx);

        btn_startStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!startStop) {
                    startStop = true;
                    btn_startStop.setText("Stop");

                    createNewFile();

                    startLocationTracking();


                } else {

                    // speicher array in csv

                    startStop = false;
                    btn_startStop.setText("Start");

                    printToCSV(toCSVList);

                    // reset file damit ein neues file angelegt werden kann
                    file = null;
                }

            }
        });

        // -------------------------------------------------------

        // timed logging activated
        timerRunning = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // timed logging deactivated
        timerRunning = false;
    }

    void printToCSV(List<String[]>list){
        String toCSVString = "";

        for (String[]arr : list) {
            toCSVString += arr[0];
            toCSVString += arr[1];
            toCSVString += arr[2];
        }

        try {
            ////// HIER GEHTS GEWALTIG SCHIEF
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(toCSVString.getBytes());
            fos.close();
            Log.d(TAG, "WROTE CSV");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Timed Logger (GPS | Hoehenmesser)
    void logData() {

        String [] data = new String [3];
        data[0] = ""+longitude;
        data[1] = ""+latitude;
        data[2] = "" + hoehe;

        toCSVList.add(data);
    }


    private void createNewFile() {

        // HIER KLEMMTS GEWALTIG

        File fileStorageDir = (this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        file = new File(fileStorageDir.getPath() + File.separator + timeStamp + ".csv");
        //this.file = new File("blaubaer.csv");
    }


    // ----------- HOEHENMESSER ------------------




    // ------------ GPS ----------------------------

    void startLocationTracking () {
        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        getGPS();
        /*
        if (!isGPSEnabled && isNetworkEnabled) {
            getGPSNetwork();
            Log.d(TAG, "NETWORK");
        } else if (!isNetworkEnabled && isGPSEnabled) {
            getGPS();
            Log.d(TAG, "GPS");
        } else {
            Log.d(TAG, "Geodaten können nicht abgerufen werden");
        }
        */
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        logData();
    }

    private void getGPSNetwork() {
        Log.d(TAG, "getGPSNetwork: Ich bin hier");

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
        Log.d(TAG, "getGPS: Ich bin hier");

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