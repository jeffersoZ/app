package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    /*
     * ---------------------------------------------------------------------------------------------
     * RESPOSTAS SOBRE LAYOUTS:
     *
     * 1. Comparação LinearLayout vs ConstraintLayout:
     *    - Performance: O ConstraintLayout é otimizado para layouts complexos, permitindo uma hierarquia plana ("flat")
     *      e evitando o aninhamento excessivo que degrada a performance no LinearLayout (problema de double taxation na medição).
     *    - Flexibilidade: ConstraintLayout é superior, oferecendo posicionamento relativo, correntes (chains), bias e guidelines.
     *      LinearLayout é restrito a orientações verticais ou horizontais.
     *    - Legibilidade: Para estruturas simples (como uma lista de itens), o LinearLayout produz um XML mais limpo e legível.
     *      O ConstraintLayout tende a ser mais verboso.
     *
     * 2. Botão A crescendo até Botão B no ConstraintLayout:
     *    Para que o Botão A ocupe todo o espaço disponível à esquerda do Botão B:
     *    - Defina a largura do Botão A como `0dp` (match_constraint).
     *    - Constraint Start: `app:layout_constraintStart_toStartOf="parent"` (ou margem desejada).
     *    - Constraint End: `app:layout_constraintEnd_toStartOf="@+id/botaoB"`.
     *    - O Botão B deve estar ancorado ao final da tela (`parent`).
     *
     * 3. LinearLayout e Propriedades:
     *    É um ViewGroup que alinha todos os filhos em uma única direção (vertical ou horizontal).
     *    Principais Propriedades:
     *      - android:orientation: Define se o fluxo é "vertical" (coluna) ou "horizontal" (linha).
     *      - android:layout_weight: Permite distribuir o espaço excedente proporcionalmente entre os filhos.
     *        Ex: Se dois botões têm weight="1", eles dividem o espaço igualmente.
     *      - android:gravity: Controla o alinhamento do conteúdo DENTRO da view (ex: texto centralizado no botão).
     *      - android:layout_gravity: Controla o alinhamento da PRÓPRIA view dentro do container pai.
     * ---------------------------------------------------------------------------------------------
     */
}