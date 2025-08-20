package com.example.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int i = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d("ciclodevida", "onCreate:bombaclat ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ciclodevida", "onStart: bombaclat");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ciclodevida", "onStop:  bombaclat2");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ciclodevida", "onRestart: bl=ombalcad3");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ciclodevida", "onDestroy: bomasdasd");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                textView.setText(String.valueOf(i));
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}