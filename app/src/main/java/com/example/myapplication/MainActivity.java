package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class MainActivity extends AppCompatActivity {

    SimplePaint simplePaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        simplePaint = findViewById(R.id.simplePaint);

        // Configuração do Color Picker
        findViewById(R.id.btCor).setOnClickListener(v -> {
            new ColorPickerDialog.Builder(this)
                    .setTitle("Selecione a Cor")
                    .setPreferenceName("MyColorPickerDialog")
                    .setPositiveButton("Confirmar",
                            (ColorEnvelopeListener) (envelope, fromUser) -> setColor(envelope))
                    .setNegativeButton("Cancelar",
                            (dialogInterface, i) -> dialogInterface.dismiss())
                    .attachAlphaSlideBar(true)
                    .attachBrightnessSlideBar(true)
                    .setBottomSpace(12)
                    .show();
        });

        // Botões de Formas
        findViewById(R.id.btLivre).setOnClickListener(v ->
                simplePaint.setShapeType(SimplePaint.ShapeType.FREEHAND));

        findViewById(R.id.btRetangulo).setOnClickListener(v ->
                simplePaint.setShapeType(SimplePaint.ShapeType.RECTANGLE));

        findViewById(R.id.btCirculo).setOnClickListener(v ->
                simplePaint.setShapeType(SimplePaint.ShapeType.CIRCLE));

        // Ações de Camada
        findViewById(R.id.btDesfazer).setOnClickListener(v -> simplePaint.undo());

        findViewById(R.id.btLimpar).setOnClickListener(v -> simplePaint.clear());
    }

    public void setColor(ColorEnvelope envelope) {
        simplePaint.setColor(envelope.getColor());
    }

    /*
     * ==========================================================
     * EXPLICAÇÕES SOBRE VIEWS CUSTOMIZADAS E EVENTOS DE TOQUE
     * ==========================================================
     *
     * 1. Como criar uma View Customizada (ex: Botão que muda de cor e se move):
     * -------------------------------------------------------------------------
     * Para criar um componente totalmente novo, estendemos a classe View.
     *
     * Exemplo conceitual para um botão móvel:
     *
     * public class MovingButton extends View {
     *     // Construtores obrigatórios...
     *
     *     @Override
     *     public boolean onTouchEvent(MotionEvent event) {
     *         float x = event.getRawX(); // Posição X absoluta na tela
     *
     *         switch (event.getAction()) {
     *             case MotionEvent.ACTION_DOWN:
     *                 // Tocou no botão: Mudar cor para indicar seleção
     *                 this.setBackgroundColor(Color.RED);
     *                 break;
     *
     *             case MotionEvent.ACTION_MOVE:
     *                 // Arrastou o dedo: Atualizar posição da view
     *                 // "Levar para esquerda ou direita" significa alterar a propriedade X
     *                 this.setX(x - (getWidth() / 2)); // Centraliza o botão no dedo
     *                 break;
     *
     *             case MotionEvent.ACTION_UP:
     *                 // Soltou: Voltar cor original
     *                 this.setBackgroundColor(Color.BLUE);
     *                 break;
     *         }
     *         return true; // Indica que o evento foi consumido por nós
     *     }
     * }
     *
     * 2. Como funciona o sistema de eventos de toque (onTouchEvent):
     * --------------------------------------------------------------
     * O método onTouchEvent(MotionEvent event) é o coração da interatividade em Views customizadas.
     * O sistema operacional monitora a tela touch e envia "eventos" para a View que está sob o dedo.
     *
     * Principais tipos de ação (event.getAction()):
     * - ACTION_DOWN: O dedo tocou a tela. É o início de tudo. Aqui inicializamos variáveis (ex: startX, startY).
     * - ACTION_MOVE: O dedo se moveu mantendo contato. Aqui atualizamos coordenadas para desenhar ou mover objetos.
     * - ACTION_UP: O dedo saiu da tela. Aqui finalizamos a ação (ex: salvar o desenho na camada).
     *
     * Como usar para desenhar (Exemplo do SimplePaint):
     * a) No ACTION_DOWN: Criamos um novo caminho (Path) e movemos o cursor para onde o dedo tocou (moveTo).
     * b) No ACTION_MOVE: Adicionamos linhas ao caminho seguindo o dedo (lineTo) e chamamos invalidate().
     * c) O invalidate() força o Android a chamar o onDraw() novamente, atualizando a tela visualmente.
     *
     * 3. Por que é necessário sobrescrever onDraw()?
     * ----------------------------------------------
     * Uma View padrão (classe base) é essencialmente um retângulo vazio e transparente. O Android não sabe
     * o que você quer mostrar nela a menos que você desenhe manualmente.
     *
     * O método onDraw(Canvas canvas) fornece:
     * - Canvas: A "folha de papel" ou tela de pintura. É onde desenhamos.
     * - Paint: (Que criamos no código) O "pincel" que define cor, espessura, estilo, etc.
     *
     * Fluxo:
     * onTouchEvent detecta movimento -> Atualiza dados (Path) -> Chama invalidate() -> Android chama onDraw() -> onDraw usa Canvas para desenhar o Path atualizado.
     */
}
