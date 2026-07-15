package com.duoc.sistemainscripcion.cursos.service;

import com.duoc.sistemainscripcion.cursos.model.Inscripcion;
import com.duoc.sistemainscripcion.cursos.repository.InscripcionRepositorio;
import com.duoc.sistemainscripcion.rabbitmq.InscripcionProductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InscripcionServicio {

    @Autowired
    private InscripcionRepositorio inscripcionRepositorio;

    @Autowired
    private InscripcionProductor inscripcionProductor;

    public Inscripcion crear(Inscripcion inscripcion) {
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setEstado("PENDIENTE");
        Inscripcion guardada = inscripcionRepositorio.save(inscripcion);
        inscripcionProductor.publicarInscripcion(guardada);
        return guardada;
    }

    public Inscripcion obtener(Long id) {
        return inscripcionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada: " + id));
    }

    public Inscripcion modificar(Long id, Inscripcion datos) {
        Inscripcion inscripcion = obtener(id);
        inscripcion.setNombreEstudiante(datos.getNombreEstudiante());
        inscripcion.setEmailEstudiante(datos.getEmailEstudiante());
        inscripcion.setNombreCurso(datos.getNombreCurso());
        inscripcion.setInstructor(datos.getInstructor());
        inscripcion.setEstado(datos.getEstado());
        return inscripcionRepositorio.save(inscripcion);
    }

    public void eliminar(Long id) {
        inscripcionRepositorio.deleteById(id);
    }

    public List<Inscripcion> consultarPorInstructorYCurso(String instructor, String nombreCurso) {
        return inscripcionRepositorio.findByInstructorAndNombreCurso(instructor, nombreCurso);
    }

    public List<Inscripcion> listarTodas() {
        return inscripcionRepositorio.findAll();
    }
}
