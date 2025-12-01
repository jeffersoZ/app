package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor sensorLuz;
    TextView valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        valor = findViewById(R.id.valor);
        
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // "Registrar um Listener": Inscrevemos esta Activity para receber avisos do sensor.
        // SENSOR_DELAY_NORMAL: Intervalo de ~200ms. Ideal para sensores de ambiente (Luz, Temperatura)
        // que não mudam bruscamente, economizando processamento.
        sm.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
     sm.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float luz = event.values[0];
        valor.setText(luz + " lux");
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * RESPOSTAS:
     *
     * 1. O que significa "registrar um Listener" e como economizar bateria?
     *    - Registrar (registerListener) significa avisar ao Sistema Android (SensorManager) que
     *      queremos começar a receber dados de um sensor específico.
     *    - Para economizar bateria, DEVEMOS parar de escutar o sensor quando o app não está visível.
     *      Isso é feito chamando `unregisterListener` dentro do método `onPause()`.
     *      No código acima, movi o registro para `onResume` e o cancelamento para `onPause` para ilustrar isso.
     *
     * 2. Influência do tipo de sensor no intervalo (SENSOR_DELAY_...):
     *    A escolha do "delay" define a frequência com que o `onSensorChanged` é chamado.
     *    - SENSOR_DELAY_NORMAL (200ms): Usado aqui para Luz. Baixo consumo. Bom para mudanças lentas.
     *    - SENSOR_DELAY_UI (60ms): Para interações de tela (ex: girar o celular).
     *    - SENSOR_DELAY_GAME (20ms): Para jogos (ex: controlar carro inclinando). Alto consumo.
     *    - SENSOR_DELAY_FASTEST (0ms): O mais rápido possível. Apenas para dados de alta precisão. Drena a bateria rapidamente.
     *
     *    Escolhemos NORMAL para o sensor de LUZ porque a luminosidade do ambiente não costuma mudar
     *    milisegundo a milisegundo, então não precisamos gastar bateria lendo freneticamente.
     * ---------------------------------------------------------------------------------------------
     */
}
