package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    EditText nome;
    Button saveButton;
    ListView lista;
    ArrayList<Nota> notasLista;

    NotasAdapter adapter;

    public static class Nota {
        long id;
        String nome;
        String texto;

        public Nota(long id, String nome, String texto) {
            this.id = id;
            this.nome = nome;
            this.texto = texto;
        }

        public long getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getTexto() {
            return texto;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.edNome);
        saveButton = findViewById(R.id.btSalva);
        lista = findViewById(R.id.lista);
        notasLista = new ArrayList<>();

        database = openOrCreateDatabase("app_database", MODE_PRIVATE, null);

        database.execSQL("create table if not exists notas (" +
                         "id integer primary key autoincrement," +
                         "name varchar, texto varchar)");

        saveButton.setOnClickListener(v -> {
            String texto = nome.getText().toString();
            if (!texto.isEmpty()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", texto);
                contentValues.put("texto", texto); // Adiciona o texto (usando o mesmo valor do nome)
                database.insert("notas", null, contentValues); // Insere os dados na tabela "notas"
                nome.setText("");
                carregarNotas();
            }
        });

        carregarNotas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarNotas();
    }

    public void carregarNotas() {
        notasLista.clear();

        Cursor cursor = database.rawQuery("SELECT * FROM notas", null);

        int idIndex = cursor.getColumnIndex("id");
        int nameIndex = cursor.getColumnIndex("name");
        int textoIndex = cursor.getColumnIndex("texto");

        if (cursor.moveToFirst()) {
            do {
                if (idIndex != -1 && nameIndex != -1 && textoIndex != -1) {
                    long id = cursor.getLong(idIndex);
                    String name = cursor.getString(nameIndex);
                    String texto = cursor.getString(textoIndex);
                    notasLista.add(new Nota(id, name, texto));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        if (adapter == null) {
            adapter = new NotasAdapter(this, notasLista, database);
            lista.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
