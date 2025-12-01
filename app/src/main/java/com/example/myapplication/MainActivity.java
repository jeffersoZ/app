package com.example.myapplication;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list;
    List<Planet> planets = new ArrayList<>();
    PlanetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.listaUsers);

        String[] namesPlanets = {
                "Mercurio",
                "Vênus",
                "Terra",
                "Marte",
                "Jupiter",
                "Saturno",
                "Urano",
                "Netuno"
        };

        int[] planetsImage = {
                R.drawable.mercury,
                R.drawable.venus,
                R.drawable.earth,
                R.drawable.mars,
                R.drawable.jupter,
                R.drawable.saturn,
                R.drawable.uranus,
                R.drawable.neptune
        };

        for(int i = 0; i < 8; i++) {
            Planet planet = new Planet(namesPlanets[i], planetsImage[i]);
            planets.add(planet);
        }

        adapter = new PlanetAdapter(this, planets);
        list.setAdapter(adapter);
    }
}