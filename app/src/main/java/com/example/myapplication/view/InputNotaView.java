package com.example.myapplication.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.control.NotaController;
import com.example.myapplication.model.Nota;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InputNotaView extends AppCompatActivity {
    Button botaoSalvar;
    EditText tituloView;
    EditText textoView;
    NotaController notaController;

    // Já explicado no adapter
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    // id da nota em edição (null = criação)
    private Integer editingId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Defino qual vai ser o layout utilizado na minha view
        setContentView(R.layout.activity_input_nota);

        notaController = new NotaController(this);

        // Isso aqui é que nem JS, a gente captura o elemento dps atribui ou recupera valor
        botaoSalvar = findViewById(R.id.botaoSalvar);
        tituloView = findViewById(R.id.inputTituloNota);
        textoView = findViewById(R.id.inputTextNota);

        // Captura o bundle vindo de outras telas
        Bundle b = getIntent().getExtras();

        // Setta o isEdit como false
        boolean isEdit = false;

        // Se houver bundle
        if (b != null) {
            // Recebe o valor de isEdit que vem do inputNotaView, se não houver valor, por padrão irá false
            isEdit = b.getBoolean("edicao", false);
            // se isEdit for true
            if (isEdit) {
                // pega id, se existir
                editingId = b.containsKey("idNota") ? b.getInt("idNota") : null;
            }
        }

        // Se realmente for uma tela de edição
        if (isEdit && editingId != null) {
            // Desabilita o botão para executar operações
            botaoSalvar.setEnabled(false);

            tituloView.setText(b.getString("titulo"));
            textoView.setText(b.getString("texto"));
            botaoSalvar.setEnabled(true);
        }

        botaoSalvar.setOnClickListener(v -> {
            // Bloquear o botão
            v.setEnabled(false);

            // Pega os valores dos campos como ternário, se os campos forem nulos, o valor será uma string vazia
            String novoTitulo = tituloView.getText() == null ? "" : tituloView.getText().toString();
            String novoTexto = textoView.getText() == null ? "" : textoView.getText().toString();

            // Se ao clicar em salvar não for atribuido nenhum id ao edintingId ele criará uma nova nota
            if (editingId != null) {
                // Cria um novo objeto de nota, passando o id, titulo e texto
                Nota notaAtualizada = new Nota(editingId, novoTitulo, novoTexto);

                // Executa uma thread para atualizar a nota em segundo plano
                executor.execute(() -> {
                    try {
                        // Atualiza a nota
                        notaController.atualizarNota(notaAtualizada);

                        // No main thread seta o resultado e finaliza a activity
                        mainHandler.post(() -> {
                            setResult(RESULT_OK);
                            finish();
                        });
                    } catch (Exception e) {
                        Log.e("InputNotaView", "Erro ao atualizar nota", e);
                        mainHandler.post(() -> v.setEnabled(true));
                    }
                });
            } else {
                // Criando nova nota caso o editingId seja nulo
                Nota nova = new Nota();
                nova.setTitulo(novoTitulo);
                nova.setTexto(novoTexto);

                // Thread parar executar função de banco de dados
                executor.execute(() -> {
                    try {
                        // Cadastra a nova nota
                        notaController.cadastrarNovaNota(nova);

                        // Na main thread seta o resultado como OK e finaliza a activity
                        mainHandler.post(() -> {
                            setResult(RESULT_OK);
                            finish();
                        });
                    } catch (Exception e) {
                        Log.e("InputNotaView", "Erro ao cadastrar nova nota", e);
                        mainHandler.post(() -> v.setEnabled(true));
                    }
                });
            }
        });
    }

    // Finaliza a nova thread ao destruir a view
    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
