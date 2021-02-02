package com.example.abgabe_4.database.entities;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.abgabe_4.database.util.Converters;
import com.example.abgabe_4.database.util.Koordinate;
import com.example.abgabe_4.database.util.Generator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity(tableName="route")
@TypeConverters({Converters.class})
public class Route {

    private static final String TAG = "Route: ";
    @NonNull
    @PrimaryKey
    String bezeichnung;

    @ColumnInfo
    Koordinate beginn;

    @ColumnInfo
    Koordinate ende;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[]gpx;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[]image;

    @ColumnInfo
    double dauer;

    // Helper Variable for graph display
    @ColumnInfo
    public List <Koordinate> koordinates;

    // Constructors
    public Route(){
        Log.d(TAG, "Route: Random Route_Name was assigned");
        this.bezeichnung = "Route_" + Generator.INSTANCE.getRandomNumberAsString();
    }

    public Route(@NotNull String routenName){
        if (routenName != null || routenName.equals("Name der Route")) {
            this.bezeichnung = routenName;
        // wenn der name der route leer oder auf dem initial wert ist
        }else {
            this.bezeichnung = "Route_" + Generator.INSTANCE.getRandomNumberAsString();
        }
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setKoordinateList(List <Koordinate> koordinateList) {
        this.koordinates = koordinateList;
    }

    public List<Koordinate>getKoordinateList(){
        return this.koordinates;
    }
}
