package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    EditText nome;
    Button saveButton;
    ListView lista;
    ArrayList<String> notasLista;

    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        nome = findViewById(R.id.edNome);
        saveButton = findViewById(R.id.btSalva);
        lista = findViewById(R.id.lista);
        notasLista = new ArrayList<>();


        database = openOrCreateDatabase("app_database",MODE_PRIVATE,null);

        database.execSQL("create table if not exists notas (" +
                         "id integer primary key autoincrement," +
                         "name varchar, texto varchar)");

        saveButton.setOnClickListener(v -> {
            String texto = nome.getText().toString();
            if(!texto.isEmpty()){
                ContentValues contentValues=new ContentValues();
                contentValues.put("name", texto);
                contentValues.put("texto", texto);
                database.insert("notas",null,contentValues);
            }
        });
        carregarNotas();
    }

    public void carregarNotas(){
        Cursor cursor = database.rawQuery("Select * from notas",null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int columnIndex= cursor.getColumnIndex("texto");
            String s = cursor.getString(columnIndex);
            notasLista.add(s);
            cursor.moveToNext();
        }

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notasLista);
        lista.setAdapter(adapter);
    }
}