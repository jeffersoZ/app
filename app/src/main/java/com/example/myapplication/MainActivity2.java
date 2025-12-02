package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    TextView tPeso, tAltura, tImc;
    ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tPeso = findViewById(R.id.tvPeso);
        tAltura = findViewById(R.id.tvaltura);
        tImc = findViewById(R.id.tvIMC);
        imagem = findViewById(R.id.imagem);


        Bundle b = getIntent().getExtras();


        float altura = 0;
        float peso = 0;
        
        if (b != null) {
            altura = b.getFloat("altura");
            peso = b.getFloat("peso");
        }

        float imc = (peso) / (altura * altura);

        tPeso.setText(Float.toString(peso));
        tAltura.setText(Float.toString(altura));
        tImc.setText(Float.toString(imc));

        if (imc < 18.5) {
            imagem.setImageResource(R.drawable.abaixopeso);
            imagem.setContentDescription("Imagem ilustrativa: Abaixo do peso");
        } else if (imc < 24.9) {
            imagem.setImageResource(R.drawable.normal);
            imagem.setContentDescription("Imagem ilustrativa: Peso normal");
        } else if (imc < 29.9) {
            imagem.setImageResource(R.drawable.sobrepeso);
            imagem.setContentDescription("Imagem ilustrativa: Sobrepeso");
        } else if (imc < 34.9) {
            imagem.setImageResource(R.drawable.obesidade1);
            imagem.setContentDescription("Imagem ilustrativa: Obesidade grau 1");
        } else if (imc < 39.9) {
            imagem.setImageResource(R.drawable.obesidade2);
            imagem.setContentDescription("Imagem ilustrativa: Obesidade grau 2");
        } else {
            imagem.setImageResource(R.drawable.obesidade3);
            imagem.setContentDescription("Imagem ilustrativa: Obesidade grau 3");
        }
    }
}

/*
 * ---------------------------------------------------------------------------------------------
 * RESPOSTAS TÉCNICAS:
 *
 * 1. Intent e Serialização:
 *    Utilizamos Intents porque o Android gerencia o ciclo de vida das Activities. Não podemos simplesmente
 *    dar "new MainActivity2()". A Intent é uma mensagem ao sistema operacional solicitando a troca de tela.
 *    Os dados são serializados (convertidos em pacotes de bytes primitivos) dentro de um Bundle para que possam
 *    ser transportados pelo sistema, inclusive entre processos diferentes, se necessário.
 *
 * 2. Passagem de Dados:
 *    É feita através do método `putExtra(chave, valor)` na Activity de origem e recuperada via
 *    `getIntent().getExtras()` na Activity de destino (como feito acima nas linhas 26-35).
 *
 * 3. Exemplo "ola" e "boa tarde":
 *    Bastaria adicionar múltiplos extras na Intent de origem:
 *    intent.putExtra("SAUDACAO_1", "ola");
 *    intent.putExtra("SAUDACAO_2", "boa tarde");
 *
 * 4. Acoplamento Fraco:
 *    Activities não conhecem as variáveis internas umas das outras. A MainActivity1 não acessa
 *    diretamente `MainActivity2.tPeso`. Ela apenas envia uma mensagem (Intent). Se você mudar
 *    o nome da variável `tPeso` aqui, a MainActivity1 continua funcionando. Isso facilita manutenção.
 *
 * 5. Uso de res/drawable:
 *    Melhora a organização separando lógica (Java) de recursos (Imagens).
 *    O Android escolhe automaticamente a melhor resolução da imagem dependendo da densidade da tela do celular,
 *    garantindo que o visual fique nítido em qualquer dispositivo.
 *
 * 6. Acessibilidade (Importante!):
 *    Observe o uso de `imagem.setContentDescription(...)` no código acima (linhas 45-60).
 *    Isso é fundamental para deficientes visuais. Leitores de tela (TalkBack) leem esse texto
 *    em voz alta para descrever a imagem, já que eles não podem vê-la.
 * ---------------------------------------------------------------------------------------------
 */
