package com.duoc.sistemainscripcion.cursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.sistemainscripcion.cursos.model.Curso;
import com.duoc.sistemainscripcion.cursos.service.CursoServicio;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoServicio cursoServicio;

    // CREATE
    @PostMapping
    public ResponseEntity<Curso> crearCurso(@RequestBody Curso curso) {

        Curso nuevoCurso = cursoServicio.guardarCurso(curso);

        return new ResponseEntity<>(nuevoCurso, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos() {

        return ResponseEntity.ok(cursoServicio.obtenerCursos());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCurso(@PathVariable Long id) {

        Curso curso = cursoServicio.obtenerCursoPorId(id);

        return ResponseEntity.ok(curso);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizarCurso(
            @PathVariable Long id,
            @RequestBody Curso curso) {

        Curso actualizado = cursoServicio.actualizarCurso(id, curso);

        return ResponseEntity.ok(actualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {

        cursoServicio.eliminarCurso(id);

        return ResponseEntity.noContent().build();
    }
}