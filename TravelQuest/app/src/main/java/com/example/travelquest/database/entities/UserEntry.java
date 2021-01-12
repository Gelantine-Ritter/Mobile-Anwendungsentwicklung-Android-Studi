package com.example.travelquest.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userEntry")
public class UserEntry {
    static int userCount = 0;

    // gets called on app start
    public UserEntry() {
        this.userID = userCount++;
    }

    @PrimaryKey
    public int userID;
    @ColumnInfo(name = "dauer")
    int dauer;
    @ColumnInfo(name = "budget")
    int budget;
    @ColumnInfo(name = "f1")
    boolean f1;
    @ColumnInfo(name = "f2")
    boolean f2;
    @ColumnInfo(name = "f3")
    boolean f3;
    @ColumnInfo(name = "f4")
    boolean f4;
    @ColumnInfo(name = "f5")
    boolean f5;

    // GETTER SETTER
    public int getUserID() {
        return userID;
    }

    public int getDauer() {
        return dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
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
}

