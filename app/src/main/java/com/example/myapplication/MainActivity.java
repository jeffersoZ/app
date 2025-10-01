package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button b;

    TextView altura, peso, nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = findViewById(R.id.button);
        altura = findViewById(R.id.edAltura);
        peso = findViewById(R.id.edPeso);
        nome = findViewById(R.id.edNome);
        b.setOnClickListener(v->{
            Intent itent = new Intent(this,MainActivity2.class);

            float fPeso = Float.parseFloat(peso.getText().toString());
            float fAltura = Float.parseFloat(altura.getText().toString());
            String sNome = nome.getText().toString();
            float imc = (fPeso)/fAltura*fAltura;

            getIntent().putExtra("altura",fAltura);
            getIntent().putExtra("peso",fPeso);
            getIntent().putExtra("imc",imc);
            getIntent().putExtra("nome",sNome);
            startActivity(itent);
        });
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("altura",altura.getText().toString());
        outState.putString("peso",peso.getText().toString());
        outState.putString("nome",nome.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            altura.setText(savedInstanceState.getString("altura"));
            peso.setText(savedInstanceState.getString("peso"));
            nome.setText(savedInstanceState.getString("nome"));
        }
    }
}