package com.example.myrouteplam.ui.neue_route;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrouteplam.MainActivity;
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
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    LocationManager locationManager;
    double longitude;
    double latitude;

    Context c;
    String timestampNow;

    List<String[]> dataPointList;
    byte[] gpxFile;

    private NeueRouteViewModel neueRouteViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        neueRouteViewModel = new ViewModelProvider(this).get(NeueRouteViewModel.class);
        View root = inflater.inflate(R.layout.neue_route, container, false);
        final TextView textView = root.findViewById(R.id.txtView_neue_route);
        Button btn_startRoute = root.findViewById(R.id.btn_neue_route);
        init();
/**
 * Methode zeichnet nach Betätigung die Route auf.
 * Die einzelnen Daten werden ausgelesen und dem Routen Objekt für die Datenbank übergeben.
 */
        btn_startRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aufnahme der Route
                if (!startStop) {
                    startStop = true;
                    btn_startRoute.setText("Stop");
                    textView.setText("I'm tracking your steps.");
                    initLocationTracking();
                } else {
                    logDataEnde();
                    startStop = false;
                    btn_startRoute.setText("Start");
                    textView.setText("");

                    generateGPX(dataPointList);


                    Route routeObj = new Route();
                    routeObj.setBeginn(longLatStart);
                    routeObj.setBezeichnung("Route " + routeObj.getId());
                    routeObj.setDauer((double) getDateDiff(timeStart, timeStop));
                    routeObj.setEnde(longLatStop);
                    routeObj.setGpxData(gpxFile);
                    //routeObj.setPois();

                    //Obj der Datenbank hinzufügen/pushen
                    MainActivity.routeDB.routeDao().addRoute(routeObj);

                    Toast.makeText(getActivity(), "Route hinzugefügt", Toast.LENGTH_SHORT).show();
                    clearData();
                }
            }
        });


        neueRouteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    /**
     * Methode initialisiert die Vorgänge damit eine Route aufgezeichnet wird
     */
    void init() {
        startStop = false;
        c = getActivity();
        locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        routeBegin = true;
        dataPointList = new ArrayList<String[]>();
    }


    /**
     * Methode setzt Variablen auf Initialwerte.
     */
    void clearData() {
        routeBegin = true;
        dataPointList = new ArrayList<String[]>();
        startStop = false;
    }


    /**
     * Die Methode ruft Geodaten per Location Manager ab.
     * Es wird je nach Verfügung GPS oder das Netzwerk verwendet.
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
     * In der Methode werden die Daten (Koordinaten und Timestamp) vorbereitet und
     * Startwerte für die Datenbank werden extrahiert.
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

    /**
     * In dieser Methode werden Endwerte nach dem loggen der Route extrahiert
     */
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
     * Methode erstellt eine GPX Datei aus den getrackten Daten und loggt zeilenwise die Route.
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