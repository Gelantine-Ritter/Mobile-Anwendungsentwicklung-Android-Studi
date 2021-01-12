package com.example.travelquest.database.logic;

import android.util.Log;

import com.example.travelquest.database.dao.TQDao;
import com.example.travelquest.database.db.TQDatabase;
import com.example.travelquest.database.entities.Destination;

public enum DestinationBuilder {
    INSTANCE;
    private static final String TAG = "DestinationBuilder: ";

    public void createDestinationEntries(TQDatabase db, TQDao tqDao) {
        if (!(tqDao.getRowCount() > 0)) {
            tqDao.addDestination(
                    // IBIZA
                    new Destination("Ibiza", 7, 2000, false, false, false, false, true),
                    // NORDPOL
                    new Destination("Nordpol", 14, 3000, false, true, true, false, false),
                    // PARIS
                    new Destination("Paris", 7, 800, true, false, false, true, true),
                    // THAILAND
                    new Destination("Thainland", 14, 1000, false, false, false, false, false),
                    // BRANDENBURG
                    new Destination("Brandenburg", 3, 100, true, true, true, true, true),
                    // NEW YORK
                    new Destination("New York", 10, 1500, true, false, false, false, true),
                    // PRAG
                    new Destination("Prag", 5, 200, true, true, false, true, true)
            );
            Log.d(TAG, " - - - - - - - - - - - - Initialized new Destination Objects");
        } else {
            Log.d(TAG, " - - - - - - - - - - - - Already initialized Objects");
        }
    }
}