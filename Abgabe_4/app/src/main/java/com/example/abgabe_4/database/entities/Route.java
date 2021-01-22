package com.example.abgabe_4.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.abgabe_4.database.util.Koordinate;
import com.example.abgabe_4.database.util.Generator;

import java.util.List;

@Entity(tableName="route")
public class Route {



    @NonNull
    @PrimaryKey
    String bezeichnung;

    @ColumnInfo
    Koordinate beginn;

    @ColumnInfo
    Koordinate ende;

    @ColumnInfo
    byte[]gpx;

    @ColumnInfo
    double dauer;

    @ColumnInfo
    List<Poi> pois;

    public Route(){
        this.bezeichnung = "Route_" + Generator.INSTANCE.getRandomNumberAsString();
    }


    // GETTER / SETTER


    @NonNull
    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(@NonNull String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Koordinate getBeginn() {
        return beginn;
    }

    public void setBeginn(Koordinate beginn) {
        this.beginn = beginn;
    }

    public Koordinate getEnde() {
        return ende;
    }

    public void setEnde(Koordinate ende) {
        this.ende = ende;
    }

    public byte[] getGpx() {
        return gpx;
    }

    public void setGpx(byte[] gpx) {
        this.gpx = gpx;
    }

    public double getDauer() {
        return dauer;
    }

    public void setDauer(double dauer) {
        this.dauer = dauer;
    }

    public List <Poi> getPois() {
        return pois;
    }

    public void setPois(List <Poi> pois) {
        this.pois = pois;
    }
}
