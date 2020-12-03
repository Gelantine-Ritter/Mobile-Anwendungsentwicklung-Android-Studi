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
import androidx.core.app.ActivityCompat;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements LocationListener{
    private static final String TAG = "";


    boolean timerRunning;

    Context context;

    Button btn_startStop;
    Switch switch1;

    TextView text;

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

    //
    List<String[]> dataPointList;

    boolean gpx_csv;


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




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_startStop = findViewById(R.id.btn_StartStop);
        btn_startStop.setText("Start");
        text = findViewById(R.id.text);
        text.setText("");
        switch1 = findViewById(R.id.csvGpx);
        switch1.setText("CSV");
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    gpx_csv = true;
                    switch1.setText("GPX");
                }else{
                    gpx_csv = false;
                    switch1.setText("CSV");
                }
            }
        });

        // --------------------------------------------------------

        init();
        startButtonActivity();

        // -------------------------------------------------------

        // timed logging activated
        timerRunning = true;
    }

    void init () {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        dataPointList = new ArrayList<String[]>();
        startStop = false;
        file = null;
    }

    // start / stop button
    // leads the app
    void startButtonActivity() {
        btn_startStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!startStop) {
                    startStop = true;
                    btn_startStop.setText("Stop");
                    text.setText("I'm tracking your steps.");
                    initLocationTracking();
                } else {
                    startStop = false;
                    btn_startStop.setText("Start");
                    text.setText("");

                    // switch

                    if (gpx_csv) {
                        generateGPX(dataPointList);
                        Log.d(TAG, "GPX PRINTED");
                    }else{
                        generateCSV(prepareDataForCSVPrinting(dataPointList));
                        Log.d(TAG, "CSV PRINTED");
                    }

                    generateGraph();
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
        file = null;
        dataPointList = null;

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

        dataPointList.add(data);
    }

    String getRandomName() {
        Random a = new Random();
        String f = "" + a.nextDouble();
        return f;
    }

    // generate GPX file
    void generateGPX (List <String []>list) {
        String track_name = getRandomName();
        String filename = "tracked_data_" + track_name  + ".gpx";

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><gpx version=\"1.1\" creator=\"gruppe-kilian-anna\">";
        String name = "<name>" + "track" + track_name + "</name><trk><trkseg>\n";

        String segments = "";

        for (String [] arr : list) {
            segments += "<trkpt lat=\"" + arr[1] + "\" lon=\"" + arr[2] + "\"><time>" + arr[0] + "</time></trkpt>\n";
        }

        String footer = "</trkseg></trk></gpx>";

        File root = getExternalFilesDir ("GPX_STORE");
        if (!root.exists()) {
            root.mkdirs();
        }

        try {
            File gxpfile = new File(root, filename);
            FileWriter writer = new FileWriter(gxpfile);

            writer.append(header);
            writer.append(name);
            writer.append(segments);
            writer.append(footer);

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // generate CSV File
    public void generateCSV(String content) {

        String filename = "tracked_data_" + timestampNow + ".csv";
        Log.d(TAG, content);

        File root = getExternalFilesDir ("CSV_STORE");
        if (root != null)
            if (!root.exists()) {
                root.mkdirs();
            }

        try {
            File csvfile = new File(root, filename);
            FileWriter writer = new FileWriter(csvfile);
            writer.append(content);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    // --------- GRAPH PLOT -------------------------------

    // paints graph with data (lat + long + time
    void generateGraph () {

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

        series.setDrawDataPoints(true);
        series.setDrawAsPath(true);

        double [] latArr = sortDataArr(dataPointList, true);
        double [] lngArr = sortDataArr(dataPointList, false);

        for (int i = 0; i < dataPointList.size(); i ++) {
            series.appendData(new DataPoint(latArr[i],lngArr[i]), false, dataPointList.size());
        }

        // set min / max values for the axis and add 100 to have a larger view of the graph (to don't cut it on the sides)
        graph.getViewport().setMinX(maxValue(latArr) + 100);
        graph.getViewport().setMinY(maxValue(lngArr)+ 100);

        // delete all possible content
        graph.removeAllSeries();
        // add / print series object (with all the data)
        graph.addSeries(series);

    }

    // returns biggest double value of an array
    public double maxValue(double array[]) {
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return Collections.max(list);
    }

    // parameter unsorted list with all data
    // returns sorted array with lat points
    // if latLong == true -> returns lat arr
    // if latLong == false -> returns long arr
    double[] sortDataArr (List <String[]>list, boolean latLong) {

        double [] xArr = new double [list.size()];

        if (latLong) {
            for (int i=0; i < xArr.length; i++){
                xArr[i] = Double.parseDouble(list.get(i)[1]);
            }
        }else{
            for (int i=0; i < xArr.length; i++){
                xArr[i] = Double.parseDouble(list.get(i)[2]);
            }
        }

        Arrays.sort(xArr);
        return xArr;
    }


    // ------------ GPS ----------------------------

    // INIT LOCATION TRACKING
    void initLocationTracking () {
        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGPSEnabled)
            getGPS();
        else
            getGPSNetwork();
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