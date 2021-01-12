package com.example.travelquest.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.travelquest.database.entities.Destination;
import com.example.travelquest.database.entities.UserEntry;
import java.util.List;
@Dao
public interface TQDao {
    // --------------------- DESTINATION ------------------------
    // ADD DESTINATION
    @Insert
    public void addDestination(Destination ...destination);
    // GET NUMBER OF DESTINATIONS
    @Query("SELECT COUNT(destinationName) FROM destinations")
    public int getRowCount();
    // GET LIST OF ALL DESTINATIONS
    @Query("SELECT * FROM destinations")
    public List<Destination>getDestinationList();
    // --------------------- USERENTRIES ------------------------
    // ADD USERENTRY
    @Insert
    public void addUserEntry(UserEntry userEntry);
    // GET USERENTRY
    @Query("SELECT * FROM userEntry")
    public UserEntry getUserEntry();
    // Remove all UserEntry(ies) - if there are mistankenly more than one entries
    @Query("DELETE FROM userEntry")
    void deleteUserEntry();
}