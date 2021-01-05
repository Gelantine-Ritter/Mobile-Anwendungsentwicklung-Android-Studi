package com.example.myrouteplam.ui.neue_route;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrouteplam.R;
import com.example.myrouteplam.entities.Route;

import java.io.File;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class NeueRouteFragment extends Fragment implements LocationListener {
    private static final String TAG = "";

    boolean routeBegin;
    Timestamp timeStart;
    Timestamp timeStop;
    String longLatStart;
    String longLatStop;


    Button btn_startStop;
    TextView text;
    boolean startStop;
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    LocationManager locationManager;
    Context c;

    String timestampNow;

    List<String[]> dataPointList;


    double longitude;
    double latitude;

    byte[] gpxFile;


    private NeueRouteViewModel neueRouteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        neueRouteViewModel = new ViewModelProvider(this).get(NeueRouteViewModel.class);
        View root = inflater.inflate(R.layout.neue_route, container, false);
        final TextView textView = root.findViewById(R.id.txtView_neue_route);
        Button btn_startRoute = root.findViewById(R.id.btn_neue_route);
/**
 * Start Button/Event um Route aufzuzeichnen
 */
        btn_startRoute.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                init();
                //Aufnahme der Route
                if (!startStop) {
                    startStop = true;
                    btn_startRoute.setText("Stop");
                    textView.setText("I'm tracking your steps.");
                    initLocationTracking();
                } else {
                    startStop = false;
                    btn_startRoute.setText("Start");
                    textView.setText("");

                    generateGPX(dataPointList);

                    Route routeObj = new Route();
                    routeObj.setBeginn(longLatStart);
                    routeObj.setBezeichnung("track"+routeObj.getId());
                    routeObj.setDauer((double) getDateDiff(timeStart, timeStop));
                    routeObj.setEnde(longLatStop);
                    routeObj.setGpxData(gpxFile);
                    //routeObj.setPois();
                }


//----------------------abspeichern----------------------------------


            }
        });


        neueRouteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {


            @Override
            public void onChanged(@Nullable String s) {


            }
        });
        return root;
    }


    void init() {
        routeBegin = true;
        c = getActivity();
        locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        dataPointList = new ArrayList<String[]>();
        startStop = false;
    }


    /**
     * Geodaten per Location Manager abrufen
     */
    void initLocationTracking() {
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

    //Case: GPS Daten über Netzwerk abrufen
    private void getGPSNetwork() {

        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


    /**
     * Methode wird via OnLocationChanged aufgerufen  und speichert die Werte zeilenweise ab
     */

    void logData() {

        this.timestampNow = String.valueOf(new Timestamp(System.currentTimeMillis()));

        String[] data = new String[4];

        data[0] = "" + this.timestampNow;
        data[1] = "" + this.longitude;
        data[2] = "" + this.latitude;

        dataPointList.add(data);

        if (routeBegin) {
            longLatStart = longitude + " " + latitude;
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


        longLatStop = longitude + " " + latitude;
        timeStop = new Timestamp(System.currentTimeMillis());


    }

    /**
     * Methode erstellt eine GPX Datei aus den getrackten Daten
     */
    void generateGPX(List<String[]> list) {
        String track_name = getRandomName();
        String filename = "tracked_data_" + track_name + ".gpx";

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><gpx version=\"1.1\" creator=\"gruppe-kilian-anna\">";
        String name = "<name>" + "track" + track_name + "</name><trk><trkseg>\n";

        String segments = "";

        for (String[] arr : list) {
            segments += "<trkpt lat=\"" + arr[1] + "\" lon=\"" + arr[2] + "\"><time>" + arr[0] + "</time></trkpt>\n";
        }

        String footer = "</trkseg></trk></gpx>";

        String finalString = header + name + segments + footer;
        gpxFile = finalString.getBytes();


    }


    String getRandomName() {
        Random a = new Random();
        String f = "" + a.nextDouble();
        return f;
    }

    public static long getDateDiff(Timestamp oldTs, Timestamp newTs) {
        long diffInMS = newTs.getTime() - oldTs.getTime();
        return TimeUnit.MINUTES.convert(diffInMS, TimeUnit.SECONDS);

    }
}