package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    TextView tPeso, tAltura, tImc;
    ImageView imagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPeso = findViewById(R.id.tvPeso);
        tAltura = findViewById(R.id.tvaltura);
        tImc = findViewById(R.id.tvIMC);

        imagem = findViewById(R.id.imagem);

        setContentView(R.layout.activity_main2);
        Bundle b = getIntent().getExtras();

        float altura = b.getFloat("altura");
        float peso = b.getFloat("peso");

        float imc = (peso)/altura*altura;

        tPeso.setText(Float.toString(peso));
        tAltura.setText(Float.toString(altura));
        tImc.setText(Float.toString(imc));

        if(imc < 18.5){
            imagem.setImageResource(R.drawable.abaixopeso);
        }else{
            imagem.setImageResource(R.drawable.sobrepeso);
        }

    }
}