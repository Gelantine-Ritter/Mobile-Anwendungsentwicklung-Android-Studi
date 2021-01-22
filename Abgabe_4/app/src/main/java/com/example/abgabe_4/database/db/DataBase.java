package com.example.abgabe_4.database.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.abgabe_4.database.dao.PoiDao;
import com.example.abgabe_4.database.dao.RouteDao;
import com.example.abgabe_4.database.entities.Poi;
import com.example.abgabe_4.database.entities.Route;

@Database(entities = {Route.class, Poi.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract RouteDao routeDao();
    public abstract PoiDao poiDao();
}
