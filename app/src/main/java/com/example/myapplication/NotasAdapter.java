package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.MainActivity.Nota;

import java.util.ArrayList;

public class NotasAdapter extends ArrayAdapter<Nota> {

    private final Context context;
    private final ArrayList<Nota> notas;
    private final SQLiteDatabase database;

    public NotasAdapter(Context context, ArrayList<Nota> notas, SQLiteDatabase database) {
        super(context, R.layout.list_item_nota, notas);
        this.context = context;
        this.notas = notas;
        this.database = database;
    }

    private static class ViewHolder {
        TextView textViewNota;
        Button buttonEditar;
        Button buttonDelete;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_nota, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textViewNota = convertView.findViewById(R.id.textViewNota);
            viewHolder.buttonEditar = convertView.findViewById(R.id.buttonEditar);
            viewHolder.buttonDelete = convertView.findViewById(R.id.buttonDelete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Nota nota = notas.get(position);

        viewHolder.textViewNota.setText(nota.getTexto());

        viewHolder.buttonEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditarNotaActivity.class);
            intent.putExtra("NOTA_ID", nota.getId());
            intent.putExtra("NOTA_NOME", nota.getNome());
            intent.putExtra("NOTA_TEXTO", nota.getTexto());
            context.startActivity(intent);
        });

        viewHolder.buttonDelete.setOnClickListener(v -> {
            database.delete("notas", "id = ?", new String[]{String.valueOf(nota.getId())});
            notas.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}
