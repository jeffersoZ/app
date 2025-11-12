package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditarNotaActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editTexto;
    private Button buttonSalvar;
    private SQLiteDatabase database;
    private long notaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nota);

        editNome = findViewById(R.id.editNome);
        editTexto = findViewById(R.id.editTexto);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        database = openOrCreateDatabase("app_database", MODE_PRIVATE, null);

        Intent intent = getIntent();
        notaId = intent.getLongExtra("NOTA_ID", -1);
        String nome = intent.getStringExtra("NOTA_NOME");
        String texto = intent.getStringExtra("NOTA_TEXTO");

        editNome.setText(nome);
        editTexto.setText(texto);

        buttonSalvar.setOnClickListener(v -> {
            String novoNome = editNome.getText().toString();
            String novoTexto = editTexto.getText().toString();

            if (!novoNome.isEmpty() && !novoTexto.isEmpty()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", novoNome);
                contentValues.put("texto", novoTexto);

                database.update("notas", contentValues, "id = ?", new String[]{String.valueOf(notaId)});

                finish();
            }
        });
    }
}
