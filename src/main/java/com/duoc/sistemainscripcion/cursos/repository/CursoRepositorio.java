package com.duoc.sistemainscripcion.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duoc.sistemainscripcion.cursos.model.Curso;

public interface CursoRepositorio extends JpaRepository<Curso, Long> {

}
