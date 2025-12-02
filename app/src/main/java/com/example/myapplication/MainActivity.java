package com.example.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        TextView mini = findViewById(R.id.minimo);
        TextView maxi = findViewById(R.id.maximo);
        result = findViewById(R.id.textView2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min, max;
                min = Integer.parseInt(mini.getText().toString());
                max = Integer.parseInt(maxi.getText().toString());
                Random random = new Random();
                // 1. Garantindo o intervalo específico
                result.setText(String.valueOf(random.nextInt((max - min) + 1) + min));
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("sorteado",result.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            result.setText(savedInstanceState.getString("sorteado"));
        }
    }
    
    /*
     * RESPOSTAS ÀS PERGUNTAS TEÓRICAS:
     *
     * 1. Como garantir que números aleatórios estejam dentro de um intervalo específico?
     *    Utilizamos a fórmula matemática aplicada no método `nextInt(int bound)` da classe Random.
     *    O método `nextInt(N)` retorna um número entre 0 (incluso) e N (excluso).
     *    Para obter um intervalo entre MIN e MAX:
     *      - Calculamos o tamanho do intervalo: `(max - min) + 1`.
     *      - Geramos o número: `random.nextInt((max - min) + 1)`. Isso dá um valor entre 0 e (max-min).
     *      - Somamos o mínimo: `+ min`. Isso desloca o intervalo para começar em MIN.
     *    Fórmula final: `random.nextInt((max - min) + 1) + min`.
     *
     * 2. Recuperação de dados de um EditText e Conversão:
     *    - Recuperação: Usamos o método `getText()`, que retorna um objeto `Editable` (ou CharSequence).
     *    - Conversão para String: É necessário chamar `.toString()` para obter o texto puro.
     *    - Natureza dos dados: O dado retornado é sempre textual (String).
     *    - Para fazer contas: Precisamos converter essa String para um tipo numérico (int, double, etc.)
     *      usando métodos auxiliares como `Integer.parseInt(string)` ou `Double.parseDouble(string)`.
     *
     * 3. Vinculação de código a um evento do sistema:
     *    Isso é feito através de "Listeners" (Ouvintes).
     *    No código acima, usamos o método `setOnClickListener` no botão.
     *    Passamos para ele uma implementação da interface `View.OnClickListener`.
     *    Quando o sistema detecta o evento de toque (click), ele chama automaticamente o método `onClick(View v)`
     *    que definimos, executando o código contido nele.
     */
}
