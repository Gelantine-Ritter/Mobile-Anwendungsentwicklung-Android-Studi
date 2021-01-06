package com.example.myrouteplam.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrouteplam.entities.Route;

import java.util.List;

@Dao
public interface RouteDao {
    @Insert
    public void addRoute(Route route);

    // from -- *tablename*
    @Query("select * from routen")
    public List<Route> getRouten();
}