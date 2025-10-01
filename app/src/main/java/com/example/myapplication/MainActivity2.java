package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    TextView  tImc, tNome, tvPesoGanhar;
    ImageView imagem;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        float pesoGanhar;
        super.onCreate(savedInstanceState);
        tNome = findViewById(R.id.tvNome);
        tImc = findViewById(R.id.tvImc);

        imagem = findViewById(R.id.imagem);

        setContentView(R.layout.activity_main2);
        Bundle b = getIntent().getExtras();


        float altura = b.getFloat("altura");
        float peso = b.getFloat("peso");
        float imc = b.getFloat("imc");
        String nome = b.getString("nome");

        tImc.setText(Float.toString(imc));
        tNome.setText(nome);

        if(imc <= 18.5){
            imagem.setImageResource(R.drawable.abaixopeso);
        }else if(imc > 18.5 & imc <= 21){
            imagem.setImageResource(R.drawable.normal);
        }else if(imc > 21 & imc <= 25){
            imagem.setImageResource(R.drawable.sobrepeso);
        }else if(imc > 25 & imc <= 27){
            imagem.setImageResource(R.drawable.obesidade1);
        }else if(imc >= 27 & imc <= 30){
            imagem.setImageResource(R.drawable.obesidade2);
        }else{
            imagem.setImageResource(R.drawable.obesidade3);
        }
        if(imc < 20){
            pesoGanhar = (20*(altura*altura)) - peso;
            tvPesoGanhar.setText(Float.toString(pesoGanhar));
        }else if(imc > 25){
            pesoGanhar = (25*(altura*altura)) - peso;
            tvPesoGanhar.setText(Float.toString(pesoGanhar));
        }else{
            tvPesoGanhar.setText(Float.toString(0));
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("imc",tImc.getText().toString());
        outState.putString("nome",tNome.getText().toString());
        outState.putString("pesoGanhar",tvPesoGanhar.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            tImc.setText(savedInstanceState.getString("imc"));
            tNome.setText(savedInstanceState.getString("nome"));
            tvPesoGanhar.setText(savedInstanceState.getString("pesoGanhar"));
        }
    }
}