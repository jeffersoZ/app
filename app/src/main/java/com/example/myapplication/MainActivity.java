package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] alunos = {"abacate1", "Abacate2", "Abacate3", "Abacate4", "Abacate5", "Abacate6"};

        listView = findViewById(R.id.listView);

        /*
         * RESPOSTAS:
         *
         * 1. Papel do ArrayAdapter:
         * O ArrayAdapter atua como uma ponte (adaptador) entre a fonte de dados (neste caso, o array de Strings 'alunos')
         * e o componente de interface do usuário (ListView). Ele é responsável por pegar cada item do array,
         * criar (ou reciclar) uma View para ele e preencher essa View com os dados correspondentes.
         *
         * 2. Parâmetros do ArrayAdapter:
         * - Context (this): O contexto atual (Activity). Necessário para acessar recursos do sistema e inflar layouts.
         * - int resource (R.layout.item_1): O ID do arquivo de layout XML que define como cada linha da lista será visualizada.
         * - int textViewResourceId (android.R.id.text1): O ID do TextView dentro do layout acima onde o texto do item será colocado.
         * - T[] objects (alunos): O array de dados que será exibido na lista.
         *
         * 3. Por que o Context é necessário?
         * O Context é fundamental porque o ArrayAdapter precisa dele para obter o 'LayoutInflater' do sistema.
         * O LayoutInflater é o serviço responsável por ler o XML do layout (R.layout.item_1) e transformá-lo em
         * objetos View reais na memória (processo chamado de inflar). Sem o Context, o Adapter não conseguiria
         * criar as visualizações para as linhas da lista.
         */
        adapter = new ArrayAdapter<>(
                this,                 // Context: Contexto para inflar o layout
                R.layout.item_1,      // Resource: Layout de cada item da lista
                android.R.id.text1,   // TextViewResourceId: ID do TextView onde o dado vai
                alunos                // Objects: Os dados
        );

        listView.setAdapter(adapter);
    }
}
