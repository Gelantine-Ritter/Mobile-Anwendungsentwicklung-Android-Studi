package com.example.travelquest.database.logic;

import android.util.Log;
import com.example.travelquest.database.dao.TQDao;
import com.example.travelquest.database.entities.Destination;
import com.example.travelquest.database.entities.UserEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public enum DestinationCalculator {
    INSTANCE;
    private static final String TAG = "DestinationCalculator: ";
    List<DestinationPercentage> sortedPercentageDestination;
    // calculates the destinations fitting percentage to the users entries (max 100)
    public List<DestinationPercentage> calculatePercentage(TQDao tqDao) {

        sortedPercentageDestination = new ArrayList<DestinationPercentage>();
        List<Destination> destinationList = tqDao.getDestinationList();
        UserEntry userEntry = tqDao.getUserEntry();
        for (Destination d : destinationList) {
            double score = 0;
            if (d.getMinDauer() >= userEntry.getDauer()) score += 25;
            if (d.getMinBudget() <= userEntry.getBudget()) score += 25;
            if ((d.isF1() && userEntry.isF1()) || (!d.isF1() && !userEntry.isF1())) score += 10;
            if ((d.isF2() && userEntry.isF2()) || (!d.isF2() && !userEntry.isF2())) score += 10;
            if ((d.isF3() && userEntry.isF3()) || (!d.isF3() && !userEntry.isF3())) score += 10;
            if ((d.isF4() && userEntry.isF4()) || (!d.isF4() && !userEntry.isF4())) score += 10;
            if ((d.isF5() && userEntry.isF5()) || (!d.isF5() && !userEntry.isF5())) score += 10;
            sortedPercentageDestination.add(new DestinationPercentage(d.getDestinationName(), score));
        }
        Collections.sort(sortedPercentageDestination);
        Log.d(TAG, this.toString());
        return sortedPercentageDestination;
    }
    @Override
    public String toString() {
        String returnString = "";
        for (DestinationPercentage dp : sortedPercentageDestination){
            returnString += dp.destinationName + ": " +  dp.score + "\n";
        }
        return returnString;
    }
}