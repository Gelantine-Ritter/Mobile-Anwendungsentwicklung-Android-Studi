package com.example.woche1aufgabe3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText text_eingabe;
    TextView text_ausgabe;

    Button toFahrenheit;
    Button toCelsius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Eingabe und Ausgabe Feld wird im Code iniziiert (zum späteren Auslesen/Überschreiben
        text_eingabe = (EditText)findViewById(R.id.text_eingabe);
        text_ausgabe = (TextView)findViewById(R.id.text_ausgabe);

        // Buttons werden für Code iniziiert
        toFahrenheit = (Button)findViewById(R.id.button_to_fahrenheit);
        toCelsius = (Button)findViewById(R.id.button_to_celsius);

        // Event/Clicklistener wird für Fahrenheitberechnung hinzugefügt
        // bei Click -> Auslesen des Eingabefelds und Berechnung, Ausgabefeld wird mit ergebnis gefüllt
        toFahrenheit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String eingabe = text_eingabe.getText().toString();
                int result = (Integer.parseInt(eingabe) - 32) * 5/9;
                String final_result = String.valueOf(result);
                text_ausgabe.setText(final_result);
            }
        });

        // Event/Clicklistener wird für Celsiusberechnung hinzugefügt
        // bei Click -> Auslesen des Eingabefelds und Berechnung, Ausgabefeld wird mit ergebnis gefüllt
        toCelsius.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String eingabe = text_eingabe.getText().toString();
                int result = (Integer.parseInt(eingabe) * 9/5) + 32;
                String final_result = String.valueOf(result);
                text_ausgabe.setText(final_result);
            }
        });
    }
}