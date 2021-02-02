package com.example.abgabe_4.database.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Generator {
    INSTANCE;

    public String getRandomNumberAsString() {
        Random random = new Random();
        return "" + random.nextInt();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDateAsString(){
        return "" + LocalDateTime.now();
    }

    // returns a list of sorted koordinates (lat_smallest + long_smallest) ++ ...
    public List<Koordinate> getSortedKoordinateList(List<Koordinate>koordinateList){
        List<Koordinate>sortedKoordinateList = new ArrayList<Koordinate>();
        double [] lat_values = new double[koordinateList.size()];
        double [] long_values = new double[koordinateList.size()];
        for ( int i = 0; i < koordinateList.size(); i++) {
            lat_values[i] = koordinateList.get(i).getLatitude();
            long_values[i] = koordinateList.get(i).getLongitude();
        }
        Arrays.sort(lat_values);
        Arrays.sort(long_values);

        for (int j = 0; j < koordinateList.size(); j ++) {
            sortedKoordinateList.add(new Koordinate(lat_values[j], long_values[j]));
        }
        return sortedKoordinateList;
    }
}
