package com.example.abgabe_4.database.util;

public class Koordinate {

    double latitude;
    double longitude;

    // konstruktor für aufruf mit 2 double lat / long
    public Koordinate (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Konstruktor für array (länge 2) eingabe
    public Koordinate (double [] latLong) {
        if (latLong.length == 2){
            new Koordinate(latLong[0], latLong[1]);
        }
    }

    public double getLongitude () {
        return this.longitude;
    }

    public double getLatitude () {
        return this.latitude;
    }

    @Override
    public String toString () {
        return ("Longitude: " + this.longitude + "\nLatitude: " + this.latitude);
    }
}
