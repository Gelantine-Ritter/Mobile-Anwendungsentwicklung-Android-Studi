package com.example.travelquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.travelquest.database.dao.TQDao;
import com.example.travelquest.database.db.TQDatabase;
import com.example.travelquest.database.entities.UserEntry;
import com.example.travelquest.database.logic.DestinationPercentage;
import com.example.travelquest.database.util.ObjectHandler;

import java.util.ArrayList;
import java.util.List;

public class ErgebnisActivity extends AppCompatActivity {

    TQDatabase tqDatabase;
    TQDao tqDao;
    TextView textErgebnis, textErgebnis1, textErgebnis2, textErgebnis3, textProzent1, textProzent2, textProzent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ergebnis);

        textErgebnis = this.findViewById(R.id.txt_ergebnis);

        textErgebnis1 = this.findViewById(R.id.txt_ergebnis_1);
        textProzent1 = this.findViewById(R.id.txt_prozent_1);
        textErgebnis2 = this.findViewById(R.id.txt_ergebnis_2);
        textProzent2 = this.findViewById(R.id.txt_prozent_2);
        textErgebnis3 = this.findViewById(R.id.txt_ergebnis_3);
        textProzent3 = this.findViewById(R.id.txt_prozent_3);

        // FINAL STEPS / PREPARE FOR DISPLAY

        ObjectHandler.INSTANCE.addUserEntry();
        List<DestinationPercentage> destinationPercentageList = ObjectHandler.INSTANCE.calculateDestinations();

        textErgebnis.setText(destinationPercentageList.get(0).getDestinationName());

        textErgebnis1.setText(destinationPercentageList.get(0).getDestinationName());
        textProzent1.setText(String.valueOf(destinationPercentageList.get(0).getScore()));
        textErgebnis2.setText(destinationPercentageList.get(1).getDestinationName());
        textProzent2.setText(String.valueOf(destinationPercentageList.get(1).getScore()));
        textErgebnis3.setText(destinationPercentageList.get(2).getDestinationName());
        textProzent3.setText(String.valueOf(destinationPercentageList.get(2).getScore()));
    }
}