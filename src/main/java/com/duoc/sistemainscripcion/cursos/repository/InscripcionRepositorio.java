package com.duoc.sistemainscripcion.cursos.repository;

import com.duoc.sistemainscripcion.cursos.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscripcionRepositorio extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByInstructorAndNombreCurso(String instructor, String nombreCurso);
}
