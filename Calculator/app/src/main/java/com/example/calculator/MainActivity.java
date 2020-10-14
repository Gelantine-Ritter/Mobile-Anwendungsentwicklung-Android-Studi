package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

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

        btnErgebnis= (Button) findViewById((R.id.equal));

        btnC=(Button) findViewById((R.id.c));



        View.OnClickListener listener = new View.OnClickListener(){
            String input = ausgabeErgebnis.getText().toString();

            public void onClick(View v){
                switch (v.getId()){
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
                        input += "0";
                        break;
                    case R.id.addieren:
                        input += "+";
                        break;
                }
                ausgabeErgebnis.setText(input);


    /*
                if (v.getId()==R.id.eins){
                    ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"1");
                }
                if (v.getId()==R.id.zwei){
                    ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"2");
                }
                if (v.getId()==R.id.drei){
                    ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"3");
                }
                if (v.getId()==R.id.vier){
                    ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"4");
                }
                if(v.getId()==R.id.fuenf){
                    ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"5");
                }
                if (v.getId()==R.id.sechs){
                    ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"6");
                }
                if (v.getId()==R.id.sieben){
                    ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"7");
                }
                if (v.getId()==R.id.acht){
                    ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"8");
                }
                if (v.getId()==R.id.neun){
                    ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"9");
                }
                if (v.getId()==R.id.zero){
                    ausgabeErgebnis.setText(ausgabeErgebnis.getText()+"0");
                }
                if (v.getId()==R.id.addieren){
                    value1= Float.parseFloat((String) ausgabeErgebnis.getText());
                    addieren=true;
                    ausgabeErgebnis.setText(null);
                }
                if (v.getId()==R.id.subtrahieren){
                    value1= Float.parseFloat((String) ausgabeErgebnis.getText());
                    subtrahieren=true;
                    ausgabeErgebnis.setText(null);
                }
                if (v.getId()==R.id.multiplizieren){
                    value1= Float.parseFloat((String) ausgabeErgebnis.getText());
                    multiplizieren=true;
                    ausgabeErgebnis.setText(null);
                }
                if (v.getId()==R.id.dividieren){
                    value1= Float.parseFloat((String) ausgabeErgebnis.getText());
                    dividieren=true;
                    ausgabeErgebnis.setText(null);
                }
                if (v.getId()==R.id.equal){
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
                    }                }
                else if (v.getId()==R.id.c){
                    ausgabeErgebnis.setText("");
                }*/
            }
        };




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

        btnPlus.setOnClickListener(listener);
        btnMinus.setOnClickListener(listener);
        btnTeilen.setOnClickListener(listener);
        btnMultipl.setOnClickListener(listener);

        btnErgebnis.setOnClickListener(listener);

        btnC.setOnClickListener(listener);


    };
}



