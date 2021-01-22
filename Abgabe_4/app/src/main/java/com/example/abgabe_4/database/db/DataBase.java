package com.example.abgabe_4.database.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.abgabe_4.database.dao.RouteDao;
import com.example.abgabe_4.database.entities.Route;
import com.example.abgabe_4.database.util.Converters;

@Database(entities = {Route.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class DataBase extends RoomDatabase {
    public abstract RouteDao routeDao();
}
