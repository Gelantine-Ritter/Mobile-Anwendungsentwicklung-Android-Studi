package com.example.myrouteplam.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;


@Entity(tableName = "routen")
public class Poi {


    @PrimaryKey
    public int id;
    @ColumnInfo(name = "daten")
    private String daten;

    @ColumnInfo(name = "foto")
    private File foto;

    public Poi(String daten, File foto) {
        this.daten = daten;
        this.foto = foto;
    }
}
