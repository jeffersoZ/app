package com.example.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int i = 0;
    private TextView textView; // Variável de classe para acesso global

    // Este método geralmente só é chamado se persistableMode for definido no manifesto.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d("ciclodevida", "onCreate: persistable");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ciclodevida", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ciclodevida", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ciclodevida", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ciclodevida", "onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        // 4. Solução aplicada: Recuperando o estado salvo após a rotação
        if (savedInstanceState != null) {
            i = savedInstanceState.getInt("chave_contador");
            textView.setText(String.valueOf(i));
        }

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

    // 4. Solução aplicada: Salvando o estado antes da Activity ser destruída
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("chave_contador", i);
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * RESPOSTAS E EXPLICAÇÕES:
     *
     * 1. Estrutura da Activity e Separação de Responsabilidades:
     *    A Activity funciona como o controlador da interface. O arquivo XML define a estrutura visual (layout),
     *    enquanto a classe Java define o comportamento. Essa separação facilita a manutenção: designers podem
     *    mexer no XML sem quebrar a lógica, e desenvolvedores podem alterar a lógica sem quebrar o layout.
     *
     * 2. Importância de findViewById() e setOnClickListener():
     *    - findViewById(): É a ponte que conecta o arquivo XML ao código Java. Sem ele, o código não sabe
     *      quem é o botão ou o texto na tela.
     *    - setOnClickListener(): Define a interatividade. Ele registra um "ouvinte" que fica esperando o
     *      evento de toque. É crucial para que o app reaja às ações do usuário.
     *
     * 3. Ciclo de Vida e Persistência do Contador:
     *    O ciclo de vida gerencia o estado da Activity (criação, pausa, destruição).
     *    Quando você rotaciona a tela, o Android destrói a Activity atual (onDestroy) e cria uma nova (onCreate)
     *    para se ajustar ao novo layout. Como a variável `int i` faz parte da instância da classe, ela é perdida
     *    e reiniciada em zero na nova instância.
     *
     * 4. Solução para o Problema da Rotação:
     *    - Como resolvi no código: Utilizei o método `onSaveInstanceState` para salvar o valor de `i` num Bundle
     *      antes da destruição, e recuperei esse valor no `onCreate`.
     *    - Alternativa Elegante: A solução mais robusta e moderna recomendada pelo Google é usar **ViewModel**.
     *      O ViewModel é uma classe projetada para armazenar dados da UI e sobreviver a mudanças de configuração
     *      (como rotação) automaticamente, sem precisar salvar e restaurar manualmente via Bundle.
     * ---------------------------------------------------------------------------------------------
     */
}
