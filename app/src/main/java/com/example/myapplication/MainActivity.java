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
    EditText edTitulo, edTexto;
    Button btSalva;
    ListView listaNotepads;
    ArrayList<Notepad> notepadLista;

    NotasAdapter adapter;

    // Classe modelo agora é Notepad
    public static class Notepad {
        long id;
        String titulo;
        String texto;

        public Notepad(long id, String titulo, String texto) {
            this.id = id;
            this.titulo = titulo;
            this.texto = texto;
        }

        public long getId() { return id; }
        public String getTitulo() { return titulo; }
        public String getTexto() { return texto; }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // IDs atualizados para o contexto de bloco de notas
        edTitulo = findViewById(R.id.edTitulo);
        edTexto = findViewById(R.id.edTexto);
        btSalva = findViewById(R.id.btSalva);
        listaNotepads = findViewById(R.id.listaNotepads);
        notepadLista = new ArrayList<>();

        database = openOrCreateDatabase("app_database", MODE_PRIVATE, null);

        // Tabela agora é 'notepad' com colunas 'titulo' e 'texto'
        database.execSQL("create table if not exists notepad (" +
                         "id integer primary key autoincrement," +
                         "titulo varchar, texto varchar)");

        btSalva.setOnClickListener(v -> {
            String titulo = edTitulo.getText().toString();
            String texto = edTexto.getText().toString();

            if (!titulo.isEmpty()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("titulo", titulo);
                contentValues.put("texto", texto);
                
                database.insert("notepad", null, contentValues);
                
                edTitulo.setText("");
                edTexto.setText("");
                carregarNotepads();
            }
        });

        carregarNotepads();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarNotepads();
    }

    public void carregarNotepads() {
        notepadLista.clear();

        // Consulta na tabela 'notepad'
        Cursor cursor = database.rawQuery("SELECT * FROM notepad", null);

        int idIndex = cursor.getColumnIndex("id");
        int tituloIndex = cursor.getColumnIndex("titulo");
        int textoIndex = cursor.getColumnIndex("texto");

        if (cursor.moveToFirst()) {
            do {
                if (idIndex != -1 && tituloIndex != -1 && textoIndex != -1) {
                    long id = cursor.getLong(idIndex);
                    String titulo = cursor.getString(tituloIndex);
                    String texto = cursor.getString(textoIndex);
                    notepadLista.add(new Notepad(id, titulo, texto));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        if (adapter == null) {
            adapter = new NotasAdapter(this, notepadLista, database);
            listaNotepads.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * RESPOSTAS SOBRE O FUNCIONAMENTO DO SQLITE NO ANDROID:
     *
     * 1. Integração e Componentes Principais:
     *    O SQLite vem embutido no Android e não requer configuração de servidor. Sua manipulação
     *    é feita através do pacote `android.database.sqlite`.
     *    Componentes:
     *      - SQLiteOpenHelper: Classe abstrata responsável por criar o banco de dados e gerenciar
     *        suas versões (onCreate, onUpgrade). É a porta de entrada.
     *      - SQLiteDatabase: Classe que representa o banco em si. Possui os métodos para executar
     *        comandos SQL (insert, update, delete, query, execSQL).
     *      - ContentValues: Estrutura de chave/valor usada para passar dados para métodos de inserção e atualização.
     *        A chave é o nome da coluna e o valor é o dado a ser salvo.
     *
     * 2. Passagem de Parâmetros (Sem concatenação):
     *    Nunca concatenamos strings em SQL para evitar falhas de segurança (SQL Injection) e erros de sintaxe (aspas).
     *    Usamos o placeholder `?` na string SQL (cláusula WHERE) e passamos os valores em um array de Strings à parte.
     *
     *    Exemplo (Deletar uma nota específica baseada em ID e Título):
     *      String whereClause = "id = ? AND titulo = ?";
     *      String[] whereArgs = new String[] { String.valueOf(notaId), "Compras" };
     *
     *      // O Android substitui o primeiro ? pelo primeiro item do array, e assim por diante.
     *      db.delete("tabela_notas", whereClause, whereArgs);
     *
     * 3. Estrutura de Dados Retornada (Cursor):
     *    Ao fazer uma consulta (`query` ou `rawQuery`), o retorno é um objeto do tipo **Cursor**.
     *
     *    - O que é: O Cursor é uma interface que fornece acesso de leitura e escrita aleatória ao conjunto de resultados.
     *      Ele funciona como um ponteiro para as linhas da tabela no banco.
     *    - Por que essa estrutura: Por eficiência de memória. O banco não carrega todos os milhares de registros
     *      para a memória RAM de uma vez. O Cursor carrega sob demanda conforme você navega.
     *
     *    - Como manipulamos (Ler todos os registros):
     *      Cursor cursor = db.rawQuery("SELECT * FROM notas", null);
     *      if (cursor.moveToFirst()) { // Move o cursor para a primeira linha
     *          do {
     *              // Recupera os dados das colunas pelo índice
     *              int id = cursor.getInt(cursor.getColumnIndex("id"));
     *              String texto = cursor.getString(cursor.getColumnIndex("texto"));
     *              // Adiciona na lista de objetos...
     *          } while (cursor.moveToNext()); // Move para a próxima linha até acabar
     *      }
     *      cursor.close(); // Importante fechar para liberar recursos
     * ---------------------------------------------------------------------------------------------
     */
}
