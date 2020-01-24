package com.example.myapplication.ui.standard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class StandardFragment extends Fragment {

    private StandardViewModel standardViewModel;
    String res = "";
    //TODO en cuenta almaceno toda la cuenta
    private double cuenta = 0;
    TextView text_cuenta, text_num_introd;
    Button  bot_sum, bot_res, bot_mul, bot_div, bot_ac, bot_cero, bot_uno, bot_dos, bot_tres, bot_cuatro, bot_cinco,
            bot_seis, bot_siete, bot_ocho, bot_nueve, bot_igual, bot_borrar, bot_porc, bot_punto;
    boolean empieza = true;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        standardViewModel =
                ViewModelProviders.of(this).get(StandardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_standard, container, false);

        text_cuenta=(TextView) root.findViewById(R.id.text_cuenta);
        text_num_introd=(TextView) root.findViewById(R.id.text_num_introd);

        //C borro uno
        //TODO borrar solo uno
        bot_borrar=(Button) root.findViewById(R.id.bot_borrar);
        bot_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_cuenta.setText(text_cuenta.getText().toString().substring(0,text_cuenta.getText().length()-1));
                text_num_introd.setText(text_num_introd.getText().toString().substring(0,text_num_introd.getText().toString().length()-1));
            }
        });

        //Div
        bot_div=(Button) root.findViewById(R.id.bot_div);
        bot_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                } else {
                    cuenta = Double.valueOf((String) text_num_introd.getText()).doubleValue();
                    text_num_introd.setText("");
                    text_cuenta.append("/");
                    res = "/";
                }
            }
        });

        //Mult
        bot_mul=(Button) root.findViewById(R.id.bot_mul);
        bot_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                } else {
                    cuenta = Double.valueOf((String) text_num_introd.getText()).doubleValue();
                    text_num_introd.setText("");
                    text_cuenta.append("x");
                    res = "x";
                }
            }
        });

        //7
        bot_siete=(Button) root.findViewById(R.id.bot_siete);
        bot_siete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }
                text_cuenta.append("7");
                text_num_introd.setText(text_num_introd.getText() + "7");
            }
        });

        //8
        bot_ocho=(Button) root.findViewById(R.id.bot_ocho);
        bot_ocho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }
                text_cuenta.append("8");
                text_num_introd.setText(text_num_introd.getText() + "8");
            }
        });

        //9
        bot_nueve=(Button) root.findViewById(R.id.bot_nueve);
        bot_nueve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }
                text_cuenta.append("9");
                text_num_introd.setText(text_num_introd.getText() + "9");
            }
        });

        //Res
        bot_res=(Button) root.findViewById(R.id.bot_res);
        bot_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                } else {
                    cuenta = Double.valueOf((String) text_num_introd.getText()).doubleValue();
                    text_num_introd.setText("");
                    text_cuenta.append("-");
                    res = "-";
                }
            }
        });

        //4
        bot_cuatro=(Button) root.findViewById(R.id.bot_cuatro);
        bot_cuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }
                text_cuenta.append("4");
                text_num_introd.setText(text_num_introd.getText() + "4");
            }
        });

        //5
        bot_cinco=(Button) root.findViewById(R.id.bot_cinco);
        bot_cinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }
                text_cuenta.append("5");
                text_num_introd.setText(text_num_introd.getText() + "5");
            }
        });

        //6
        bot_seis=(Button) root.findViewById(R.id.bot_seis);
        bot_seis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }
                text_cuenta.append("6");
                text_num_introd.setText(text_num_introd.getText() + "6");
            }
        });

        //Sum
        bot_sum=(Button) root.findViewById(R.id.bot_sum);
        bot_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                } else {
                    cuenta = Double.valueOf((String) text_num_introd.getText()).doubleValue();
                    text_num_introd.setText("");
                    text_cuenta.append("+");
                    res = "+";
                }
            }
        });

        //1
        bot_uno=(Button) root.findViewById(R.id.bot_uno);
        bot_uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }
                text_cuenta.append("1");
                text_num_introd.setText(text_num_introd.getText() + "1");
            }
        });

        //2
        bot_dos=(Button) root.findViewById(R.id.bot_dos);
        bot_dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }
                text_cuenta.append("2");
                text_num_introd.setText(text_num_introd.getText() + "2");
            }
        });

        //3
        bot_tres=(Button) root.findViewById(R.id.bot_tres);
        bot_tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }
                text_cuenta.append("3");
                text_num_introd.setText(text_num_introd.getText() + "3");
            }
        });

        //=
        bot_igual=(Button) root.findViewById(R.id.bot_igual);
        bot_igual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double numero = Double.valueOf((String) text_num_introd.getText()).doubleValue();

                if (res != "") {
                    if (res == "+") {
                        cuenta = numero + cuenta;
                    } else if (res == "-"){
                        cuenta = cuenta - numero;
                    } else if (res == "x"){
                        cuenta = cuenta * numero;
                    } else if(res =="/"){
                        cuenta =cuenta /numero;
                    } else if (res == "%") {
                        double resultado = cuenta * (numero / 100);
                        cuenta -=resultado;
                    }
                    
                    String decimal = String.valueOf(cuenta);
                    boolean con_decimal = decimal.endsWith(".0");
                    if(con_decimal==true){
                        int longitud = decimal.length();
                        text_num_introd.setText(decimal.substring(0,longitud-2));
                    } else{
                        text_num_introd.setText(Double.toString(cuenta));
                    }
                } else {
                    text_num_introd.getText();
                }

                if(text_num_introd.length()>15){
                    text_num_introd.setText(((String) text_num_introd.getText()).substring(0,15));
                }

                text_cuenta.setText("");
                cuenta = 0;
                res = "";
                empieza=true;
            }
        });

        //0
        bot_cero=(Button) root.findViewById(R.id.bot_cero);
        bot_cero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }
                text_cuenta.append("0");
                text_num_introd.setText(text_num_introd.getText() + "0");
            }
        });

        //.
        bot_punto=(Button) root.findViewById(R.id.bot_punto);
            bot_punto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }else {
                    text_cuenta.append(".");
                    text_num_introd.setText(text_num_introd.getText() + ".");
                }
            }
        });


        //AC all clean
        //TODO borra todo
        bot_ac=(Button) root.findViewById(R.id.bot_ac);
        bot_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuenta = 0;
                text_cuenta.setText(" ");
                text_num_introd.setText(" ");
                res="";
            }
        });

        //Porcentaje
        bot_porc=(Button) root.findViewById(R.id.bot_porc);
        bot_porc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empieza == true){
                    text_num_introd.setText("");
                    text_cuenta.setText("");
                    empieza=false;
                }else {
                    cuenta = Double.valueOf((String) text_num_introd.getText()).doubleValue();
                    text_cuenta.append("%");
                    text_num_introd.setText("");
                    res = "%";
                }
            }
        });
        //TODO No funcionan ni el % ni el .

        return root;
    }
}