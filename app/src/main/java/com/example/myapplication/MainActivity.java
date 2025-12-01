package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.control.NotaController;
import com.example.myapplication.model.Nota;
import com.example.myapplication.view.InputNotaView;
import com.example.myapplication.view.ListNotasAdapter;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // https://developer.android.com/develop/background-work/background-tasks/asynchronous/java-threads?hl=pt-br
    private final Executor executor = Executors.newSingleThreadExecutor();
    ListNotasAdapter adapter;
    NotaController notaController;
    ListView listView;
    Button botaoNovaNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notaController = new NotaController(this);
        botaoNovaNota = findViewById(R.id.botaoNovaNota);

        listView = findViewById(R.id.listViewNotas);
        adapter = new ListNotasAdapter(this, R.layout.list_notas, notaController.getListaNotas());

        listView.setAdapter(adapter);

        botaoNovaNota.setOnClickListener(v -> {
            v.setEnabled(false);
            Intent intent = new Intent(MainActivity.this, InputNotaView.class);
            intent.putExtra("edicao", false);
            startActivity(intent);
            v.setEnabled(true);
        });

    }

    // Ao voltar pra activity principal,
        // atualiza a lista de notas
        // atualiza o adapter
        // e reinicia a view com notifyDataSetChanged()
    // https://pt.stackoverflow.com/questions/115564/atualizar-listview-usando-notifydatasetchanged
    @Override
    protected void onResume() {
        super.onResume();

        // Realizando processos em segundo plano, fazendo direto dava erro direto
        executor.execute(() -> {
            List<Nota> lista = notaController.getListaNotas();

            // Função disponível no contexto do AppCompatActivity
            // Sem necessidade de instanciar um objeto mainHandler
            // Limpa o adapter e adiciona a nova lista, após isso atualiza a tela
            runOnUiThread(() -> {
                adapter.clear();
                adapter.addAll(lista);
                adapter.notifyDataSetChanged();
            });
        });
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
