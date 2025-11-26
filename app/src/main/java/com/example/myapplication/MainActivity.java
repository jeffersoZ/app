package com.example.myapplication;

import static android.os.Build.VERSION_CODES_FULL.R;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor sensorLuz;
    TextView valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R);
        valor = findViewById(R);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        sm.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float luz = event.values[0];
        valor.setText(luz+" top");
    }
}