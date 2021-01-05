package com.example.myrouteplam.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myrouteplam.dao.RouteDao;
import com.example.myrouteplam.entities.Route;


@Database(entities = {Route.class}, version = 1)
public abstract class RouteDatabase extends RoomDatabase {
    public abstract RouteDao routeDao();
}