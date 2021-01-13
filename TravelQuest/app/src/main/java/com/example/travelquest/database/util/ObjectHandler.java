package com.example.travelquest.database.util;

import android.content.Context;

import androidx.room.Room;

import com.example.travelquest.database.dao.TQDao;
import com.example.travelquest.database.db.TQDatabase;
import com.example.travelquest.database.entities.UserEntry;
import com.example.travelquest.database.logic.DestinationBuilder;
import com.example.travelquest.database.logic.DestinationCalculator;
import com.example.travelquest.database.logic.DestinationPercentage;

import java.util.List;


public enum ObjectHandler {
    INSTANCE;

    TQDatabase db;
    TQDao tqDao;
    UserEntry userEntry;

    public void initDB (android.content.Context context) {

        // DB INITIALIZATION
        db = Room.databaseBuilder(context,
        TQDatabase.class, "tq_database").allowMainThreadQueries().build();
        tqDao = db.tqDao();
        DestinationBuilder.INSTANCE.createDestinationEntries(db, tqDao);
    }

    public void initUser(){
        tqDao.deleteUserEntry();
        userEntry = new UserEntry();
    }

    public UserEntry getUserEntry(){
        if (userEntry == null) initUser();
        return userEntry;
    }

    public void addUserEntry() { this.tqDao.addUserEntry(this.userEntry);}

    public TQDatabase getDatabase() {
        return db;
    }

    public TQDao getDao() {
        return tqDao;
    }

    public List<DestinationPercentage> calculateDestinations (){
        return DestinationCalculator.INSTANCE.calculatePercentage(this.tqDao);
    }
}
