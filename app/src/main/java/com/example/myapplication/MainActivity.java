package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonFragmentA,buttonFragmentB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        buttonFragmentA=findViewById(R.id.buttonFa);
        buttonFragmentB=findViewById(R.id.buttonFb);

        buttonFragmentA.setOnClickListener(this);
        buttonFragmentB.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Fragment fragment;
        int id = v.getId();

        if (id == R.id.buttonFa) {
            fragment = new FragmentA();
            // Exemplo prático da Resposta 3 (Passagem de dados):
            // Bundle bundle = new Bundle();
            // bundle.putString("chave", "Olá Fragmento");
            // fragment.setArguments(bundle);
        } else if (id == R.id.buttonFb) {
            fragment = new FragmentB();
        } else {
            throw new IllegalStateException("Unexpected value: " + id);
        }

       FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        
        fragmentTransaction.replace(R.id.frameConteudo, fragment);
        fragmentTransaction.commit();
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * RESPOSTAS TÉCNICAS:
     *
     * 1. Papel do Fragment e Preferência sobre Activity:
     *    Um Fragment é um componente modular de UI que roda dentro de uma Activity. Pense nele como
     *    uma "sub-tela" reutilizável.
     *    Deve ser preferido quando:
     *      - Você quer criar interfaces dinâmicas (abas, navegação lateral, viewpager) sem recarregar
     *        toda a tela.
     *      - Precisa de layouts adaptativos (ex: em Tablets mostra lista e detalhe lado a lado; em
     *        Celulares mostra em telas separadas). O Fragment permite reutilizar a mesma lógica
     *        nos dois casos.
     *
     * 2. Ciclo de Vida e Cuidados:
     *    O ciclo de vida do Fragment é aninhado e dependente da Activity hospedeira.
     *    Se a Activity entra em onPause(), o Fragment também entra. Se a Activity é destruída,
     *    o Fragment também é.
     *    Cuidados:
     *      - O Fragment pode ser "desanexado" da Activity. Acessar `getActivity()` ou `getContext()`
     *        pode retornar NULL e causar crash. Sempre verifique a nulidade ou use `isAdded()`.
     *      - Não manipule elementos de UI da Activity diretamente de dentro do Fragment sem interfaces
     *        (Callback), para manter o desacoplamento.
     *
     * 3. Passagem de Dados (Activity -> Fragment):
     *    Nunca passe dados pelo construtor customizado (ex: `new FragmentA(dados)`), pois o sistema
     *    pode recriar o fragmento (rotação de tela, falta de memória) usando o construtor padrão
     *    vazio, perdendo seus dados.
     *
     *    Forma Correta (Bundle):
     *      Fragment frag = new FragmentA();
     *      Bundle args = new Bundle();
     *      args.putString("meu_dado", "valor");
     *      frag.setArguments(args);
     *
     *    Dentro do Fragment, recupera-se com: `getArguments().getString("meu_dado");`
     * ---------------------------------------------------------------------------------------------
     */
}
