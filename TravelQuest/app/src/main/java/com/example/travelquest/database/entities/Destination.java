package com.example.travelquest.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "destinations")
public class Destination {
    public Destination(String destinationName, int minDauer, int minBudget, boolean f1, boolean f2, boolean f3, boolean f4, boolean f5){
        this.destinationName = destinationName;
        this.minDauer = minDauer;
        this.minBudget = minBudget;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;
    }
    @NonNull
    @PrimaryKey
    public String destinationName;
    @ColumnInfo(name="minDauer")
    int minDauer;
    @ColumnInfo(name="minBudget")
    int minBudget;
    @ColumnInfo(name="f1")
    boolean f1;
    @ColumnInfo(name="f2")
    boolean f2;
    @ColumnInfo(name="f3")
    boolean f3;
    @ColumnInfo(name="f4")
    boolean f4;
    @ColumnInfo(name="f5")
    boolean f5;
    // GETTER SETTER
    public String getDestinationName() {
        return destinationName;
    }
    public int getMinDauer() {
        return minDauer;
    }
    public void setDauer(int dauer) {
        this.minDauer = minDauer;
    }
    public int getMinBudget() {
        return minBudget;
    }
    public void setBudget(int budget) {
        this.minBudget = minBudget;
    }
    public boolean isF1() {
        return f1;
    }
    public void setF1(boolean f1) {
        this.f1 = f1;
    }
    public boolean isF2() {
        return f2;
    }
    public void setF2(boolean f2) {
        this.f2 = f2;
    }
    public boolean isF3() {
        return f3;
    }
    public void setF3(boolean f3) {
        this.f3 = f3;
    }
    public boolean isF4() {
        return f4;
    }
    public void setF4(boolean f4) {
        this.f4 = f4;
    }
    public boolean isF5() {
        return f5;
    }
    public void setF5(boolean f5) {
        this.f5 = f5;
    }
    @Override
    public String toString() {
        return "Destination{" +
                "destinationName='" + destinationName + '\'' +
                ", minDauer=" + minDauer +
                ", minBudget=" + minBudget +
                ", f1=" + f1 +
                ", f2=" + f2 +
                ", f3=" + f3 +
                ", f4=" + f4 +
                ", f5=" + f5 +
                '}';
    }
}


















