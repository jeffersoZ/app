package com.example.myapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NotasDAO {
    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "notas_database.db";
    private static final String TABLE_NAME = "notas";

    public NotasDAO(Context context) {
        try {
            db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "titulo TEXT, " +
                    "texto TEXT" +
                    ");");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase
    // https://developer.android.com/reference/android/content/ContentValues
    // https://stackoverflow.com/questions/36856893/sqlite-database-insert-query-issue-inside-try-catch-block
    public boolean insereNovaNota(Nota nota) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("titulo", nota.getTitulo());
            contentValues.put("texto", nota.getTexto());

            return db.insert(TABLE_NAME, null, contentValues) != -1;
        } catch (SQLException e) {
            Log.e("NotasDAO", "Erro ao inserir nova nota: ", e);
            return false;
        }
    }

    public Boolean udateNota(Nota nota) {
       try {
           ContentValues contentValues = new ContentValues();
           contentValues.put("titulo", nota.getTitulo());
           contentValues.put("texto", nota.getTexto());
           String where = "id = ?";
           String[] whereArgs = {String.valueOf(nota.getIdNota())};

           // db.update retorna o numero de linhas afetadas
           return db.update(TABLE_NAME, contentValues, where, whereArgs) != 0;
       } catch (SQLException e) {
           Log.e("NotasDAO", "Erro ao atualizar nota: ", e);
           return false;
       }
    }

    public Boolean deleteNota(Nota nota) {
        try {
            String where = "id = ?";
            String[] whereArgs = {String.valueOf(nota.getIdNota())};

            // db.delete retorna o numero de linhas afetadas
            return db.delete(TABLE_NAME, where, whereArgs) != 0;
        } catch (SQLException e) {
            Log.e("NotasDAO", "Erro ao deletar nota: ", e);
            return false;
        }
    }

    public Nota getNota(Integer idNota) {
        Nota nota = null;
        Cursor cursor = null;

        try {
            String[] args = {String.valueOf(idNota)};
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", args);
            if(cursor.moveToFirst()) {
                nota = new Nota();

                int idIndex = cursor.getColumnIndex("id");
                int tituloIndex = cursor.getColumnIndex("titulo");
                int textoIndex = cursor.getColumnIndex("texto");

                nota.setIdNota(cursor.getInt(idIndex));
                nota.setTitulo(cursor.getString(tituloIndex));
                nota.setTexto(cursor.getString(textoIndex));
            }
        } catch (SQLException e) {
            Log.e("NotasDAO", "Erro ao buscar nota: ", e);
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }

        return nota;
    }

    /*
        Cursor cursor = db.query(
        TABLE_NAME, 1. Tabela
        columns,    2. Colunas
        null,       3. selection (cláusula WHERE)
        null,       4. selectionArgs (argumentos para o WHERE)
        null,       5. groupBy
        null,       6. having
        null        7. orderBy
    );
    https://stackoverflow.com/questions/1243199/how-to-perform-an-sqlite-query-within-an-android-application
    */
    public List<Nota> getListaNotas() {
        List<Nota> notas = new ArrayList<>();
        Cursor cursor = null;
        String[] columns = {"id", "titulo", "texto"};

        try {
            cursor = db.query(
                    TABLE_NAME,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if(cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int tituloIndex = cursor.getColumnIndex("titulo");
                int textoIndex = cursor.getColumnIndex("texto");
                do {
                    Nota nota = new Nota(
                            cursor.getInt(idIndex),
                            cursor.getString(tituloIndex),
                            cursor.getString(textoIndex)
                    );

                    notas.add(nota);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("NotasDAO", "Erro ao buscar lista de notas: ", e);
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }

        return notas;
    }
}
