package com.example.abgabe_4.database.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
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
}
