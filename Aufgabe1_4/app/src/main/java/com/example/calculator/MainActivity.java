package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView display;
    Button btn1, btn2,btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnPlus, btnMinus, btnTeilen, btnMultipl, btnErgebnis, btnC, btnCE;
    boolean startNew;
    String value1, value2, operation;
    double finalResult = 0;

    String input;

    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display =(TextView) findViewById(R.id.result);

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

        btnErgebnis= (Button) findViewById((R.id.equal));

        btnC=(Button) findViewById((R.id.c));
        btnCE=(Button) findViewById((R.id.ce));

        //Button Eingaben speichern und ggf verarbeiten
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                // ausgabe reset
                if (startNew) {
                    display.setText("");
                    startNew = false;
                }
                input = display.getText().toString();


                switch (v.getId()) {
                    case R.id.eins:
                        input += "1";
                        break;
                    case R.id.zwei:
                        input += "2";
                        break;
                    case R.id.drei:
                        input += "3";
                        break;
                    case R.id.vier:
                        input += "4";
                        break;
                    case R.id.fuenf:
                        input += "5";
                        break;
                    case R.id.sechs:
                        input += "6";
                        break;
                    case R.id.sieben:
                        input += "7";
                        break;
                    case R.id.acht:
                        input += "8";
                        break;
                    case R.id.neun:
                        input += "9";
                        break;
                    case R.id.zero:
                        if(input!="")input += "0";
                        break;
                        //ausgabe zurück setzen
                    case R.id.c:
                        input = "";
                        startNew = true;
                        break;
                    case R.id.ce:
                        // ausgabestring um eine stelle kürzen (nicht wenn ausgabe leer ist)
                        if(input != ""){
                            input=input.substring(0, input.length() - 1);
                        }
                        break;
                        //berechnung ausführen
                    case R.id.equal:
                        compute();
                        break;
                }

                display.setText(input);
            }



            //Calculator Berechnungen
            private void compute() {
                // get value2 für berechnung
                value2=display.getText().toString();
                //value1+2 für berechnung parsen
                //rechenoperation ausführen, ergebnis als ausgabewert anpassen
                switch (operation){
                    case "plus":
                        finalResult = Double.parseDouble(value1) + Double.parseDouble(value2);
                        input = finalResult + "";
                        Log.i(TAG, "hier wurde + gedrückt");
                        break;
                    case "mal":
                        finalResult = Double.parseDouble(value1) * Double.parseDouble(value2);
                        input = finalResult + "";
                        Log.i(TAG, "hier wurde * gedrückt");
                        break;
                    case "geteilt":
                        finalResult = Double.parseDouble(value1) / Double.parseDouble(value2);
                        input = finalResult + "";
                        Log.i(TAG, "hier wurde / gedrückt");
                        break;
                    case "minus":
                        finalResult = Double.parseDouble(value1) - Double.parseDouble(value2);
                        input = finalResult + "";
                        Log.i(TAG, "hier wurde - gedrückt");
                        break;
                }
                Log.i(TAG, "onClick Plus result: "+ finalResult);
                display.setText(finalResult +"");
            }

        };

        // set clicklisteners
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);
        btn7.setOnClickListener(listener);
        btn8.setOnClickListener(listener);
        btn9.setOnClickListener(listener);
        btn0.setOnClickListener(listener);

        btnErgebnis.setOnClickListener(listener);

        btnC.setOnClickListener(listener);
        btnCE.setOnClickListener(listener);




        //gewählte Operatoren in String speichern
        View.OnClickListener op = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startNew=true;
                value1=display.getText().toString();
                switch(v.getId()){
                    case R.id.addieren:
                        operation="plus";
                        break;
                    case R.id.subtrahieren:
                        operation="minus";
                        break;
                    case R.id.multiplizieren:
                        operation="mal";
                        break;
                    case R.id.dividieren:
                        operation="geteilt";
                        break;
                }
            };






        };


        btnMinus.setOnClickListener(op);
        btnPlus.setOnClickListener(op);
        btnTeilen.setOnClickListener(op);
        btnMultipl.setOnClickListener(op);

    }
}