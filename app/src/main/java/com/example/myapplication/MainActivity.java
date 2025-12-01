package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    // desisti, ver dps pro trabalho

    private static final int REQUEST_LOCATION = 1;
    private static final String TAG = "MainActivity";
    LocationManager locationManager;
    TextView textView;
    Button btnGetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        btnGetLocation = findViewById(R.id.buttonGetLocation);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        checkAndGetLocation();
        btnGetLocation.setOnClickListener(v -> getLocation());
    }

    public void checkAndGetLocation() {
        // Correção da lógica: verifica se NENHUMA das permissões foi concedida
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestAccess();
        } else {
            getLocation();
        }
    }

    public void getLocation() {
        // Verificação de permissão obrigatória antes de chamar métodos de localização
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestAccess();
            return;
        }

        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, location1 -> {
                double latitude = location1.getLatitude();
                double longitude = location1.getLongitude();
                textView.setText("Latitude: " + latitude + "\n Longitude: " + longitude);
            });
            if(location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                if(textView != null) {
                    textView.setText("Latitude: " + latitude + "\n Longitude: " + longitude);
                }
            }
        } catch (Exception e) {
            // throw new RuntimeException(e); // Evite crashar o app
            e.printStackTrace();
        }
    }
    
    public void requestAccess() {
        // Solicitar permissão em tempo de execução
        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_LOCATION);
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * RESPOSTAS ÀS PERGUNTAS:
     *
     * 1. Diferença entre getLastKnownLocation() e requestLocationUpdates():
     *    - `getLastKnownLocation()`: Retorna a última posição armazenada em cache pelo sistema.
     *      É rápido e não gasta bateria extra, mas pode estar desatualizado ou ser nulo (se o GPS
     *      não foi usado recentemente).
     *    - `requestLocationUpdates()`: Solicita ativamente que o hardware do GPS (ou rede) rastreie
     *      a posição. É preciso e em tempo real, mas consome mais bateria. Requer um Listener para
     *      receber os dados continuamente.
     *
     * 2. Permissões em Tempo de Execução e Riscos:
     *    - Por que solicitar: Desde o Android 6.0 (API 23), permissões consideradas "perigosas" (como localização)
     *      precisam de autorização explícita do usuário enquanto o app roda, garantindo privacidade.
     *    - Riscos: Se você tentar chamar métodos de localização sem verificar a permissão antes,
     *      o sistema lançará uma `SecurityException` e seu aplicativo irá fechar inesperadamente (crash).
     *
     * 3. Ausência de Sensores e Alternativas:
     *    - Como lidar: Verificar se o hardware existe usando `PackageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)`.
     *    - Alternativas: Usar o provedor de rede (`LocationManager.NETWORK_PROVIDER`) que usa Wi-Fi e torres de celular,
     *      ou utilizar a API `FusedLocationProviderClient` (Google Play Services) que abstrai essa complexidade
     *      e escolhe automaticamente a melhor fonte disponível.
     *
     * 4. Incorporar Mapas de Terceiros (Google Maps):
     *    - Dependência: Adicionar `implementation 'com.google.android.gms:play-services-maps:...'` no build.gradle.
     *    - API Key: Gerar uma chave no Google Cloud Console e adicioná-la ao `AndroidManifest.xml` dentro de `<meta-data>`.
     *    - Layout: Adicionar um fragmento (`SupportMapFragment`) ou `MapView` no XML da Activity.
     *    - Código: Implementar a interface `OnMapReadyCallback` na Activity e chamar `getMapAsync(this)` para
     *      receber a instância do `GoogleMap` pronta para uso (adicionar marcadores, mover câmera, etc.).
     * ---------------------------------------------------------------------------------------------
     */
}