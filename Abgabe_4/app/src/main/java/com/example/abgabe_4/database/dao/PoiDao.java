package com.example.abgabe_4.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.abgabe_4.database.entities.Poi;
import com.example.abgabe_4.database.entities.Route;

import java.util.List;

@Dao
public interface PoiDao {


    @Insert
    public void addPoi(Poi poi);

    // GET ALL ROUTEN
    @Query("SELECT * FROM poi")
    List<Route> getPois();

}
