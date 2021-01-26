package com.example.abgabe_4.database.util;

import android.util.Log;

import androidx.room.TypeConverter;

public class Converters {
    private static final String TAG = "Converters";

    @TypeConverter
    public static Koordinate doubleArrayToKoordinate( String latLong) {
        Log.d(TAG, "doubleArrayToKoordinate: latLong:: " + latLong);
        String [] splittedLatLong = latLong.split("--");
        Log.d(TAG, "doubleArrayToKoordinate: [0]:: " + splittedLatLong[0] + "[1]:: " + splittedLatLong[1] );
        return new Koordinate(Double.parseDouble(splittedLatLong[0]), Double.parseDouble(splittedLatLong[1]));
    }

    @TypeConverter
    public static String koordinateToString(Koordinate koordinate) {
        String latLong = "";
        latLong += koordinate.getLatitude();
        latLong += "-";
        latLong += koordinate.getLongitude();
        return latLong;
    }
}
