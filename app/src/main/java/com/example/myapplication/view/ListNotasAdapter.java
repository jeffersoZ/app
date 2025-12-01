package com.example.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.myapplication.R;
import com.example.myapplication.control.NotaController;
import com.example.myapplication.model.Nota;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListNotasAdapter extends ArrayAdapter<Nota> {

    // Como explicado no MainActivity, ocorriam erros ao executar processos sincronamente com o main thread nos eventos, ocorria um erro chamado ARN Input dispatching timed
    // https://developer.android.com/develop/background-work/background-tasks/asynchronous/java-threads?hl=pt-br
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Basicamente é uma forma de executar codigo na thread principal (Main Thread ou UI Thread) depois de executar algo em segundo plano
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    Context mContext;
    int layoutResourceId;
    NotaController notaController;

    public ListNotasAdapter(@NonNull Context context, int resource, @NonNull List<Nota> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.notaController = new NotaController(context);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(layoutResourceId, parent, false);
        }

        TextView idNota = view.findViewById(R.id.idNota);
        TextView  tituloNota = view.findViewById(R.id.tituloNota);

        Nota nota = getItem(position);
        idNota.setText(String.valueOf(nota.getIdNota()));
        tituloNota.setText(nota.getTitulo());

        Button btnExcluir = view.findViewById(R.id.botaoExcluir);
        Button btnEditar = view.findViewById(R.id.botaoEditar);

        btnExcluir.setOnClickListener(v -> {
            // Desabilita o botão evitando double-clicks
            v.setEnabled(false);

            // Executa a exclusão em segundo plano e atualiza o adapter após a conclusão
            executor.execute(() -> {
                notaController.excluirNota(nota);
                mainHandler.post(() -> {
                    remove(nota);
                    notifyDataSetChanged();
                });
            });

            // Habilita novamente após a exclusão
            v.setEnabled(true);
        });

        btnEditar.setOnClickListener(v -> {
            // Desabilita o botão evitando double-clicks
            v.setEnabled(false);

            // Cria a intent colocando as informações da nota editada (id, titulo e texto).
            // Também é passado o status da nota para saber que é uma edição e não uma criação.
            Intent intent = new Intent(mContext, InputNotaView.class);
            intent.putExtra("edicao", true);
            intent.putExtra("idNota", nota.getIdNota());
            intent.putExtra("titulo", nota.getTitulo());
            intent.putExtra("texto", nota.getTexto());

            // Abre a activity InputNotaView
            mContext.startActivity(intent);

            // Habilita novamente
            v.setEnabled(true);
        });

        return view;
    }

}
