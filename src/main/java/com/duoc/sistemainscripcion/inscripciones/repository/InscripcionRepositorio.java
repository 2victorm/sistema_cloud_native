package com.duoc.sistemainscripcion.inscripciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duoc.sistemainscripcion.inscripciones.model.Inscripcion;

public interface InscripcionRepositorio extends JpaRepository<Inscripcion, Long> {

}