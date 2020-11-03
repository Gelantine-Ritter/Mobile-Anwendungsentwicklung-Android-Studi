package com.example.gpsreceiver_v2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "";

    TextView lat, lng, altitude, kmh;
    LocationManager locationManager;
    float maxSpeed = 0.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lat = findViewById(R.id.textLat);
        lng = findViewById(R.id.textLong);
        altitude = findViewById(R.id.textAltitude);
        kmh = findViewById(R.id.textSpeed);

        //get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //je nach Fall/Menü Item sollen die GPS Daten abgerufen werden: über Satellit oder Netzwerkprovider
        switch (item.getItemId()) {
            case R.id.action_GPS:
                getGPS();
                Toast.makeText(getApplicationContext(), "GPS Provider", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_Network:
                getGPSNetwork();
                Toast.makeText(getApplicationContext(), "Network Provider", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    //Case: GPS Daten über Netzwerkanbieter abrufen
    private void getGPSNetwork() {
        Log.d(TAG, "getGPSNetwork: Ich bin hier");
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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

    //Standortaktualisierung: Ausgabe der Werte für Höhe, Breiten- und Längengrade und für die aktuelle Geschwindigkeit
    public void onLocationChanged(@NonNull Location location) {
        double longtitude = location.getLongitude();
        double latitude = location.getLatitude();
        //  boolean hasAltit = location.hasAltitude();
        double altit = location.getAltitude();
        float speed = location.getSpeed() * 3.6f;
        if (speed > maxSpeed)
            maxSpeed = speed;


        lat.setText(("Latitude: " + String.valueOf(latitude)));
        lng.setText(("Longitude: " + String.valueOf(longtitude)));
        altitude.setText(String.format("%.1f m", altit));
        kmh.setText(String.format("%.1f km/h", maxSpeed));

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }
}