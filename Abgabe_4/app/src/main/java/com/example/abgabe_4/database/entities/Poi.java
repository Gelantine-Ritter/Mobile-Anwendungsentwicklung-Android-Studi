package com.example.abgabe_4.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.abgabe_4.database.util.Koordinate;

@Entity(tableName = "poi")
public class Poi {

    public Poi () {

    }

    @ColumnInfo
    public String ort;

    @ColumnInfo
    public Koordinate koordinate;

    @ColumnInfo
    public String beschreibung;

    @ColumnInfo
    public byte [] foto;


    // GETTER / SETTER

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Koordinate getKoordinate() {
        return koordinate;
    }

    public void setKoordinate(Koordinate koordinate) {
        this.koordinate = koordinate;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
