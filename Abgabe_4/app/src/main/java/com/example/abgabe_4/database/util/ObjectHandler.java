package com.example.abgabe_4.database.util;

import androidx.room.Room;

import com.example.abgabe_4.database.dao.PoiDao;
import com.example.abgabe_4.database.dao.RouteDao;
import com.example.abgabe_4.database.db.DataBase;

public enum ObjectHandler {
    INSTANCE;

    DataBase db;
    PoiDao poiDao;
    RouteDao routeDao;

    public void initDB (android.content.Context context){
        db = Room.databaseBuilder(context, DataBase.class, "database").allowMainThreadQueries().build();
        poiDao = db.poiDao();
        routeDao = db.routeDao();
    }

    public PoiDao getPoiDao () {
        return this.poiDao;
    }

    public RouteDao getRouteDao () {
        return this.routeDao;
    }


}
