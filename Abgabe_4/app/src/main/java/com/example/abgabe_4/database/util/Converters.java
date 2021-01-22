package com.example.abgabe_4.database.util;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Koordinate doubleArrayToKoordinate( String latLong) {
        String [] splittedLatLong = latLong.split("-");
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
