package com.example.travelquest.database.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.travelquest.database.dao.TQDao;
import com.example.travelquest.database.entities.Destination;
import com.example.travelquest.database.entities.UserEntry;
@Database(entities = {UserEntry.class, Destination.class}, version = 1)
public abstract class TQDatabase extends RoomDatabase {
    public abstract TQDao tqDao();
}
