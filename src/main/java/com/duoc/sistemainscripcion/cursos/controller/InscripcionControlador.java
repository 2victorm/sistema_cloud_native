package com.duoc.sistemainscripcion.cursos.controller;

import com.duoc.sistemainscripcion.cursos.model.Inscripcion;
import com.duoc.sistemainscripcion.cursos.service.InscripcionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionControlador {

    @Autowired
    private InscripcionServicio inscripcionServicio;

    // POST /inscripciones/crear
    @PostMapping("/crear")
    public ResponseEntity<Inscripcion> crear(@RequestBody Inscripcion inscripcion) {
        return ResponseEntity.ok(inscripcionServicio.crear(inscripcion));
    }

    // GET /inscripciones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionServicio.obtener(id));
    }

    // PUT /inscripciones/{id}/modificar
    @PutMapping("/{id}/modificar")
    public ResponseEntity<Inscripcion> modificar(@PathVariable Long id,
                                                  @RequestBody Inscripcion datos) {
        return ResponseEntity.ok(inscripcionServicio.modificar(id, datos));
    }

    // DELETE /inscripciones/{id}/eliminar
    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        inscripcionServicio.eliminar(id);
        return ResponseEntity.ok("Inscripcion eliminada correctamente.");
    }

    // GET /inscripciones/consultar?instructor=Juan&nombreCurso=Cloud
    @GetMapping("/consultar")
    public ResponseEntity<List<Inscripcion>> consultar(
            @RequestParam String instructor,
            @RequestParam String nombreCurso) {
        return ResponseEntity.ok(inscripcionServicio.consultarPorInstructorYCurso(instructor, nombreCurso));
    }

    // GET /inscripciones/listar
    @GetMapping("/listar")
    public ResponseEntity<List<Inscripcion>> listar() {
        return ResponseEntity.ok(inscripcionServicio.listarTodas());
    }
}
