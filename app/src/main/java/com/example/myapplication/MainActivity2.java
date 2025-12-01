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

        /*
         * RESPOSTA 4: O uso de res/drawable melhora a organização separando a lógica de apresentação (imagens) do código Java.
         * Isso permite que o Android escolha automaticamente a melhor versão da imagem dependendo da densidade da tela do dispositivo.
         */
        tPeso = findViewById(R.id.tvPeso);
        tAltura = findViewById(R.id.tvaltura);
        tImc = findViewById(R.id.tvIMC);
        imagem = findViewById(R.id.imagem);

        /*
         * RESPOSTA 1 e 3: Utilizamos Intent para navegação para manter o "Acoplamento Fraco".
         * A Activity atual não precisa saber detalhes internos da próxima, apenas envia uma mensagem (Intent) ao sistema.
         * Os dados são "serializados" (convertidos em um formato padrão, via Parcelable/Bundle) para trafegar entre processos/telas do sistema.
         */
        Bundle b = getIntent().getExtras();

        /*
         * RESPOSTA 2: Passagem de dados é feita via 'extras' (chave-valor).
         * Para passar Strings "ola" e "boa tarde", na Activity anterior faríamos:
         * intent.putExtra("saudacao1", "ola");
         * intent.putExtra("saudacao2", "boa tarde");
         *
         * E aqui recuperaríamos:
         * String s1 = b.getString("saudacao1");
         */

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

        /*
         * RESPOSTA 5: Para tornar o app acessível, devemos adicionar descrições de conteúdo (ContentDescription)
         * aos elementos visuais para que leitores de tela (como TalkBack) possam descrevê-los aos usuários.
         */
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
