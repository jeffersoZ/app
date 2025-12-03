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

import com.example.myapplication.MainActivity.Notepad;

import java.util.ArrayList;

public class NotasAdapter extends ArrayAdapter<Notepad> {

    private final Context context;
    private final ArrayList<Notepad> notepads;
    private final SQLiteDatabase database;

    public NotasAdapter(Context context, ArrayList<Notepad> notepads, SQLiteDatabase database) {
        super(context, R.layout.list_item_notepad, notepads);
        this.context = context;
        this.notepads = notepads;
        this.database = database;
    }

    private static class ViewHolder {
        TextView textViewTitulo;
        Button buttonEditar;
        Button buttonDelete;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Usando o novo layout ajustado
            convertView = inflater.inflate(R.layout.list_item_notepad, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textViewTitulo = convertView.findViewById(R.id.textViewTitulo);
            viewHolder.buttonEditar = convertView.findViewById(R.id.buttonEditar);
            viewHolder.buttonDelete = convertView.findViewById(R.id.buttonDeletar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Notepad notepad = notepads.get(position);

        viewHolder.textViewTitulo.setText(notepad.getTitulo());

        viewHolder.buttonEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditarNotaActivity.class);
            intent.putExtra("NOTEPAD_ID", notepad.getId());
            intent.putExtra("NOTEPAD_TITULO", notepad.getTitulo());
            intent.putExtra("NOTEPAD_TEXTO", notepad.getTexto());
            context.startActivity(intent);
        });

        viewHolder.buttonDelete.setOnClickListener(v -> {
            // Deleta da tabela 'notepad'
            database.delete("notepad", "id = ?", new String[]{String.valueOf(notepad.getId())});
            notepads.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}
