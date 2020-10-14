package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView ausgabeErgebnis;
    Button btn1, btn2,btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnPlus, btnMinus, btnTeilen, btnMultipl, btnErgebnis, btnC, btnCE;
    boolean addieren, subtrahieren, multiplizieren, dividieren;
    float value1, value2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ausgabeErgebnis =(TextView) findViewById(R.id.result);

        btn1=(Button) findViewById((R.id.eins));
        btn2=(Button) findViewById((R.id.zwei));
        btn3=(Button) findViewById((R.id.drei));
        btn4=(Button) findViewById((R.id.vier));
        btn5=(Button) findViewById((R.id.fuenf));
        btn6=(Button) findViewById((R.id.sechs));
        btn7=(Button) findViewById((R.id.sieben));
        btn8=(Button) findViewById((R.id.acht));
        btn9=(Button) findViewById((R.id.neun));
        btn0=(Button) findViewById((R.id.zero));

        btnPlus=(Button) findViewById((R.id.addieren));
        btnMinus=(Button) findViewById((R.id.subtrahieren));
        btnTeilen=(Button) findViewById((R.id.dividieren));
        btnMultipl=(Button) findViewById((R.id.multiplizieren));

        btnErgebnis= (Button) findViewById((R.id.isEqualTo));

        btnC=(Button) findViewById((R.id.c));





        btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"2");
            }
        });



        btn3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"6");
            }
        });


        btn7.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"9");
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"0");
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    value1= Float.parseFloat((String) ausgabeErgebnis.getText());
                    addieren=true;
                    ausgabeErgebnis.setText(null);


            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    value1= Float.parseFloat((String) ausgabeErgebnis.getText());
                    subtrahieren=true;
                    ausgabeErgebnis.setText(null);


            }
        });

        btnMultipl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    value1= Float.parseFloat((String) ausgabeErgebnis.getText());
                    multiplizieren=true;
                    ausgabeErgebnis.setText(null);


            }
        });

        btnTeilen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    value1= Float.parseFloat((String) ausgabeErgebnis.getText());
                    dividieren=true;
                    ausgabeErgebnis.setText(null);


            }
        });

        btnErgebnis.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                value2= Float.parseFloat(ausgabeErgebnis.getText()+"");
               if (addieren){
                   ausgabeErgebnis.setText(value1+value2+"");
                   addieren=false;
               }
                if (subtrahieren){
                    ausgabeErgebnis.setText(value1-value2+"");
                    subtrahieren=false;
                }
                if (multiplizieren){
                    ausgabeErgebnis.setText(value1*value2+"");
                    multiplizieren=false;
                }
                if (dividieren){
                    ausgabeErgebnis.setText(value1/value2+"");
                    dividieren=false;
                }
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ausgabeErgebnis.setText("");
            }
        });
    };
}



