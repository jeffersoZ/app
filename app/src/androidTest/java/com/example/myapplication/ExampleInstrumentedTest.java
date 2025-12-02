package com.example.myapplication;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication", appContext.getPackageName());
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
