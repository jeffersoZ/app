package com.example.myapplication.control;

import android.content.Context;

import com.example.myapplication.model.Nota;
import com.example.myapplication.model.NotasDAO;

import java.util.ArrayList;
import java.util.List;

public class NotaController {
    NotasDAO notasDAO;
    public NotaController(Context context) {
        this.notasDAO = new NotasDAO(context);
    }

    public Nota cadastrarNovaNota(Nota nota) {
        notasDAO.insereNovaNota(nota);
        return nota;
    }

    public Boolean atualizarNota(Nota nota) {
        return notasDAO.udateNota(nota);
    }

    public Nota getNota(Integer idNota) {
        return notasDAO.getNota(idNota);
    }

    public List<Nota> getListaNotas() {
        return notasDAO.getListaNotas();
    }

    public boolean excluirNota(Nota nota) {
        return notasDAO.deleteNota(nota);
    }
}
