package com.duoc.sistemainscripcion.cursos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.sistemainscripcion.cursos.model.Curso;
import com.duoc.sistemainscripcion.cursos.repository.CursoRepositorio;

@Service
public class CursoServicio {

    @Autowired
    private CursoRepositorio cursoRepositorio;

    // CREATE
    public Curso guardarCurso(Curso curso) {
        return cursoRepositorio.save(curso);
    }

    // READ ALL
    public List<Curso> obtenerCursos() {
        return cursoRepositorio.findAll();
    }

    // READ BY ID
    public Curso obtenerCursoPorId(Long id) {
        return cursoRepositorio.findById(id).orElse(null);
    }

    // UPDATE
    public Curso actualizarCurso(Long id, Curso cursoActualizado) {

        Curso curso = cursoRepositorio.findById(id).orElse(null);

        if (curso == null) {
            return null;
        }

        curso.setNombre(cursoActualizado.getNombre());
        curso.setInstructor(cursoActualizado.getInstructor());
        curso.setDuracionHoras(cursoActualizado.getDuracionHoras());
        curso.setCosto(cursoActualizado.getCosto());

        return cursoRepositorio.save(curso);
    }

    // DELETE
    public void eliminarCurso(Long id) {
        cursoRepositorio.deleteById(id);
    }
}