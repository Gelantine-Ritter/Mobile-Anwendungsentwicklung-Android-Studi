package com.example.abgabe_4.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.abgabe_4.database.entities.Route;
import com.example.abgabe_4.database.util.Converters;

import java.util.List;

@Dao
@TypeConverters({Converters.class})
public interface RouteDao {

    // ADD EINE ROUTE
    @Insert
    public void addRoute(Route route);

    // GET ALL ROUTEN
    @Query("SELECT * FROM route")
    List<Route> getRouten();

    // GET EINE ROUTE
    @Query("SELECT * FROM route WHERE bezeichnung = :bezeichnung")
    Route getRoute(String bezeichnung);

}
