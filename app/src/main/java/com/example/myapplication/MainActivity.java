package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button b;

    TextView altura, peso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = findViewById(R.id.button);
        altura = findViewById(R.id.edAltura);
        peso = findViewById(R.id.edPeso);
        b.setOnClickListener(v->{
            Intent itent = new Intent(this,MainActivity2.class);

            float fPeso = Float.parseFloat(peso.getText().toString());
            float fAltura = Float.parseFloat(altura.getText().toString());

            getIntent().putExtra("altura",fAltura);
            getIntent().putExtra("peso",fPeso);
            startActivity(itent);
        });
    }
}