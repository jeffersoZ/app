package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditarNotaActivity extends AppCompatActivity {

    private EditText editTitulo;
    private EditText editTexto;
    private Button buttonSalvar;
    private SQLiteDatabase database;
    private long notepadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nota);

        editTitulo = findViewById(R.id.editTitulo); // IDs ajustados no layout
        editTexto = findViewById(R.id.editTexto);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        
        database = openOrCreateDatabase("app_database", MODE_PRIVATE, null);

        Intent intent = getIntent();
        // Recebendo as novas chaves de Intent
        notepadId = intent.getLongExtra("NOTEPAD_ID", -1);
        String titulo = intent.getStringExtra("NOTEPAD_TITULO");
        String texto = intent.getStringExtra("NOTEPAD_TEXTO");

        editTitulo.setText(titulo);
        editTexto.setText(texto);

        buttonSalvar.setOnClickListener(v -> {
            String novoTitulo = editTitulo.getText().toString();
            String novoTexto = editTexto.getText().toString();

            if (!novoTitulo.isEmpty()) {
                ContentValues contentValues = new ContentValues();
                // Colunas ajustadas para a tabela 'notepad'
                contentValues.put("titulo", novoTitulo);
                contentValues.put("texto", novoTexto);

                database.update("notepad", contentValues, "id = ?", new String[]{String.valueOf(notepadId)});

                finish();
            }
        });
    }
}
