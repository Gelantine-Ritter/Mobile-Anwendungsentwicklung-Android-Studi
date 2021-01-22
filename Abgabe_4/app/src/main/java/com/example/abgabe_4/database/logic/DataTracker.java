package com.example.abgabe_4.database.logic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.abgabe_4.database.entities.Route;
import com.example.abgabe_4.database.util.Koordinate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DataTracker implements LocationListener {

    Context context;

    boolean routeBegin;

    LocationManager locationManager;
    List<String[]> dataPointList;
    Timestamp timeStart;
    Timestamp timeStop;
    public Koordinate longLatStart, longLatStop;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    double longitude, latitude;
    String timestampNow;

    byte [] gpxFile;

    public double dauer;

    public DataTracker (Context context) {
        this.context = context;

        clearData();
        prepare();
    }

    public void start () {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        initLocationTracking();

    }

    public void stop(){
        logDataEnde();
    }

    void prepare() {
        context = context.getApplicationContext();
        routeBegin = true;
        dataPointList = new ArrayList<String[]>();
    }

    void clearData() {
        routeBegin = true;
        dataPointList = new ArrayList<String[]>();
    }

    // -------------- LOGGING ---------------------

    void logData() {

        this.timestampNow = String.valueOf(new Timestamp(System.currentTimeMillis()));

        String[] data = new String[4];

        data[0] = "" + this.timestampNow;
        data[1] = "" + this.longitude;
        data[2] = "" + this.latitude;

        dataPointList.add(data);

        if (routeBegin) {
            longLatStart = new Koordinate(latitude, longitude);
            timeStart = new Timestamp(System.currentTimeMillis());
            routeBegin = false;
        }
    }

    public void logDataEnde() {
        this.timestampNow = String.valueOf(new Timestamp(System.currentTimeMillis()));

        String[] data = new String[4];

        data[0] = "" + this.timestampNow;
        data[1] = "" + this.longitude;
        data[2] = "" + this.latitude;

        dataPointList.add(data);

        longLatStop = new Koordinate(latitude, longitude);
        timeStop = new Timestamp(System.currentTimeMillis());
        dauer = (double) getDateDiff(timeStart, timeStop);

    }


    // ---------------- LOCATION -------------------


    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        // --------------- HERE HAPPENS THE MAGIC ---------------------
        logData();
    }

    void initLocationTracking() {
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled)
            getGPS();
        else
            getGPSNetwork();
    }



    //Case: GPS Daten über Netzwerk abrufen
    private void getGPSNetwork() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    // ----- GENERATE GPX ---------------



    /**
     * Methode erstellt eine GPX Datei aus den getrackten Daten und loggt zeilenwise die Route.
     */
    public byte [] generateGPX() {
        String track_name = getRandomName();
        String filename = "tracked_data_" + track_name + ".gpx";

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><gpx version=\"1.1\" creator=\"gruppe-kilian-anna\">";
        String name = "<name>" + "track" + track_name + "</name><trk><trkseg>\n";

        String segments = "";

        for (String[] arr : dataPointList) {
            segments += "<trkpt lat=\"" + arr[1] + "\" lon=\"" + arr[2] + "\"><time>" + arr[0] + "</time></trkpt>\n";
        }

        String footer = "</trkseg></trk></gpx>";

        String finalString = header + name + segments + footer;
        gpxFile = finalString.getBytes();
        return gpxFile;
    }

    /**
     * Methode erzeugt IDs/Namen für die GPX getrackten Routen
     *
     * @return Filename
     */
    String getRandomName() {
        Random a = new Random();
        String f = "" + a.nextDouble();
        return f;
    }

    /**
     * Die Methode berechnet die Differenz der Start- und Endwerte der getrackten Route.
     *
     * @param oldTs Startwerte der Timestamp
     * @param newTs Endwerte der Timestamp
     * @return
     */
    public static long getDateDiff(Timestamp oldTs, Timestamp newTs) {
        long diffInMS = newTs.getTime() - oldTs.getTime();
        return TimeUnit.MINUTES.convert(diffInMS, TimeUnit.SECONDS);

    }



}
