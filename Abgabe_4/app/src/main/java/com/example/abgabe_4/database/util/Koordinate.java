package com.example.abgabe_4.database.util;

public class Koordinate {

    double longitude;
    double latitude;

    public Koordinate (double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString () {
        return ("Longitude: " + this.longitude + "\nLatitude: " + this.latitude);
    }
}
