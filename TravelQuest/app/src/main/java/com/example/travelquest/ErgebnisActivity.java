package com.example.travelquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.travelquest.database.dao.TQDao;
import com.example.travelquest.database.db.TQDatabase;
import com.example.travelquest.database.entities.UserEntry;
import com.example.travelquest.database.logic.DestinationPercentage;
import com.example.travelquest.database.util.ObjectHandler;

import java.util.ArrayList;
import java.util.List;

/***
 * Darstellung der Ergebnisse
 */

public class ErgebnisActivity extends AppCompatActivity {


    TextView  textErgebnis1, textErgebnis2, textErgebnis3, textProzent1, textProzent2, textProzent3;
    Button btnStartAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ergebnis);


        textErgebnis1 = this.findViewById(R.id.txt_ergebnis_1);
        textProzent1 = this.findViewById(R.id.txt_prozent_1);
        textErgebnis2 = this.findViewById(R.id.txt_ergebnis_2);
        textProzent2 = this.findViewById(R.id.txt_prozent_2);
        textErgebnis3 = this.findViewById(R.id.txt_ergebnis_3);
        textProzent3 = this.findViewById(R.id.txt_prozent_3);
        btnStartAgain = findViewById(R.id.btn_startAgain);

        // FINAL STEPS / PREPARE FOR DISPLAY

        ObjectHandler.INSTANCE.addUserEntry();
        List<DestinationPercentage> destinationPercentageList = ObjectHandler.INSTANCE.calculateDestinations();


        textErgebnis1.setText(destinationPercentageList.get(0).getDestinationName());
        textProzent1.setText(String.valueOf((int) destinationPercentageList.get(0).getScore()) + "%");
        textErgebnis2.setText(destinationPercentageList.get(1).getDestinationName());
        textProzent2.setText(String.valueOf((int)destinationPercentageList.get(1).getScore()) + "%");
        textErgebnis3.setText(destinationPercentageList.get(2).getDestinationName());
        textProzent3.setText(String.valueOf((int)destinationPercentageList.get(2).getScore()) + "%");

        btnStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ErgebnisActivity.this, MainActivity.class));

            }
        });




    }
}