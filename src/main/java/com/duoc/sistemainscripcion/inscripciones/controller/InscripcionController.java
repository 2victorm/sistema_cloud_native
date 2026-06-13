package com.duoc.sistemainscripcion.inscripciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.sistemainscripcion.cursos.model.Curso;
import com.duoc.sistemainscripcion.inscripciones.model.Inscripcion;
import com.duoc.sistemainscripcion.inscripciones.service.InscripcionServicio;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionServicio inscripcionServicio;

    // CREATE
    @PostMapping
    public ResponseEntity<Inscripcion> crearInscripcion(
            @RequestBody Inscripcion inscripcion) {

        Inscripcion nueva = inscripcionServicio.saveInscripcion(inscripcion);

        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Inscripcion>> listarInscripciones() {

        return ResponseEntity.ok(inscripcionServicio.obtenerInscripciones());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> obtenerInscripcion(
            @PathVariable Long id) {

        Inscripcion inscripcion = inscripcionServicio.obtenerInscripcionPorId(id);

        return ResponseEntity.ok(inscripcion);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> actualizarInscripcion(
            @PathVariable Long id,
            @RequestBody Inscripcion inscripcion) {

        Inscripcion actualizado = inscripcionServicio.actualizarInscripcion(id, inscripcion);

        return ResponseEntity.ok(actualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInscripcion(
            @PathVariable Long id) {

        inscripcionServicio.eliminarInscripcion(id);

        return ResponseEntity.noContent().build();
    }
}