package com.example.myrouteplam.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;
import java.sql.Timestamp;


@Entity(tableName = "routen")
public class Route {

    public static int counter = 0;

    @PrimaryKey
    public int id;
    @ColumnInfo(name = "bezeichnung")
    private String bezeichnung;

    @ColumnInfo(name = "beginn")
    private String beginn;

    @ColumnInfo(name = "ende")
    private String ende;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] gpxData;

    @ColumnInfo(name = "dauer")
    private Double dauer;

    @ColumnInfo(name = "pois")
    private Integer pois;

    public Route() {
        //wird Route erstellt, bekommt diese die Start ID und aufgrund des counters wird jeder weiteren Route die nächste freie ID zugewiesen
        this.id = counter++;
    }

    public Route(String bezeichnung, String beginn, String ende, byte[] gpx, double dauer) {
        this.bezeichnung=bezeichnung;
        this.beginn=beginn;
        this.ende=ende;
        this.gpxData=gpx;
        this.dauer = dauer;
    }

    public int getId() {
        return id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBeginn() {
        return beginn;
    }

    public void setBeginn(String beginn) {
        this.beginn = beginn;
    }

    public String getEnde() {
        return ende;
    }

    public void setEnde(String ende) {
        this.ende = ende;
    }

    public byte[] getGpxData() {
        return gpxData;
    }

    public void setGpxData(byte[] gpxData) {
        this.gpxData = gpxData;
    }

    public Double getDauer() {
        return dauer;
    }

    public void setDauer(Double dauer) {
        this.dauer = dauer;
    }

    public Integer getPois() {
        return pois;
    }

    public void setPois(Integer pois) {
        this.pois = pois;
    }
}
