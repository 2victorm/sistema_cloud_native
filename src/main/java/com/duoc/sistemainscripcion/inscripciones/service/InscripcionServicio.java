package com.duoc.sistemainscripcion.inscripciones.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.sistemainscripcion.cursos.model.Curso;
import com.duoc.sistemainscripcion.cursos.repository.CursoRepositorio;
import com.duoc.sistemainscripcion.inscripciones.model.Inscripcion;
import com.duoc.sistemainscripcion.inscripciones.repository.InscripcionRepositorio;

@Service
public class InscripcionServicio {

    @Autowired
    private InscripcionRepositorio inscripcionRepositorio;

    @Autowired
    private CursoRepositorio cursoRepositorio;

    // CREATE
    public Inscripcion saveInscripcion(Inscripcion inscripcion) {

        double total = 0;

        for (Curso curso : inscripcion.getCursos()) {

            Curso cursoCompleto = cursoRepositorio.findById(curso.getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Curso no encontrado con id: " + curso.getId()));

            total += cursoCompleto.getCosto();
        }

        inscripcion.setTotalPagar(total);

        return inscripcionRepositorio.save(inscripcion);
    }

    // READ ALL
    public List<Inscripcion> obtenerInscripciones() {
        return inscripcionRepositorio.findAll();
    }

    // READ BY ID
    public Inscripcion obtenerInscripcionPorId(Long id) {
        return inscripcionRepositorio.findById(id).orElse(null);
    }

    // UPDATE
    public Inscripcion actualizarInscripcion(Long id, Inscripcion inscripcionActualizada) {

        Inscripcion inscripcion = inscripcionRepositorio.findById(id).orElse(null);

        if (inscripcion == null) {
            return null;
        }

        inscripcion.setNombreEstudiante(inscripcionActualizada.getNombreEstudiante());
        inscripcion.setCursos(inscripcionActualizada.getCursos());
        inscripcion.setTotalPagar(inscripcionActualizada.getTotalPagar());

        return inscripcionRepositorio.save(inscripcion);
    }

    // DELETE
    public void eliminarInscripcion(Long id) {
        inscripcionRepositorio.deleteById(id);
    }
}