package com.example.abgabe_4.database.util;

import android.util.Log;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

public class Converters {
    private static final String TAG = "Converters";

    @TypeConverter
    public static Koordinate doubleArrayToKoordinate( String latLong) {
        Log.d(TAG, "doubleArrayToKoordinate: latLong:: " + latLong);
        String [] splittedLatLong = latLong.split("-");
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

    @TypeConverter
    public static String koordinateListToDoubleArrayList (List<Koordinate> koordinateList){
        String koordinatenString = "";
        for (Koordinate k : koordinateList) {
            koordinatenString += (k.latitude + "-" + k.longitude + ":");
        }

        // remove last ":" from string
        if (koordinatenString != null && koordinatenString.length() > 0 && koordinatenString.charAt(koordinatenString.length() - 1) == ':') {
            koordinatenString = koordinatenString.substring(0, koordinatenString.length() - 1);
        }
        return koordinatenString;
    }

    @TypeConverter
    public static List<Koordinate>koordinateList(String koordinatenString) {
        List<Koordinate>koordinateList = new ArrayList<Koordinate>();
        for (String s : koordinatenString.split(":")){
            String [] singleKoordinate = s.split("-");
            koordinateList.add(new Koordinate(Double.parseDouble(singleKoordinate[0]), Double.parseDouble(singleKoordinate[1])));
        }
        return koordinateList;
    }
}
