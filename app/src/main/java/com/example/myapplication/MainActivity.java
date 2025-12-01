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
        // RESPOSTA 1: O Fragment é uma porção modular da interface do usuário.
        // Ele deve ser preferido a uma Activity quando queremos reutilizar componentes de UI
        // em diferentes telas ou criar navegação dinâmica (abas, gavetas) sem recarregar toda a tela.
        Fragment fragment;
        switch (v.getId()){
            case (R.id.buttonFa):
                fragment = new FragmentA();
                break;

            case (R.id.buttonFb):
                fragment = new FragmentB();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }

        // RESPOSTA 3: Passagem de dados Activity -> Fragment
        // A forma correta é usar Bundle e setArguments.
        // Não passamos dados pelo construtor porque o Android recria o Fragment chamando o construtor vazio
        // ao girar a tela, perdendo dados passados via construtor personalizado.
        /* Exemplo:
        Bundle args = new Bundle();
        args.putString("chave", "valor");
        fragment.setArguments(args);
        */

        // RESPOSTA 2: Ciclo de vida
        // O ciclo do Fragment é atrelado à Activity (getSupportFragmentManager).
        // Se a Activity morre, os Fragments morrem.
        // Cuidado ao manipular dados: O Contexto do Fragment pode ser nulo se ele for desconectado.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        
        // Use 'replace' em vez de 'add' se quiser substituir o fragmento atual pelo novo.
        fragmentTransaction.replace(R.id.frameConteudo, fragment);
        fragmentTransaction.commit();
    }
}