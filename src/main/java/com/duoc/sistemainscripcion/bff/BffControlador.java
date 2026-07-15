package com.duoc.sistemainscripcion.bff;

import com.duoc.sistemainscripcion.cursos.model.Inscripcion;
import com.duoc.sistemainscripcion.cursos.model.InscripcionMensaje;
import com.duoc.sistemainscripcion.cursos.repository.InscripcionMensajeRepositorio;
import com.duoc.sistemainscripcion.cursos.service.InscripcionServicio;
import com.duoc.sistemainscripcion.rabbitmq.InscripcionProductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BFF (Backend for Frontend): orquesta las llamadas entre el frontend y las colas RabbitMQ.
 * Expone endpoints simplificados que internamente coordinan el flujo
 * inscripcion -> productor -> cola -> consumidor.
 */
@RestController
@RequestMapping("/bff")
public class BffControlador {

    @Autowired
    private InscripcionServicio inscripcionServicio;

    @Autowired
    private InscripcionProductor inscripcionProductor;

    @Autowired
    private InscripcionMensajeRepositorio inscripcionMensajeRepositorio;

    /**
     * Endpoint productor: recibe la inscripcion del frontend,
     * la persiste y la publica en la cola RabbitMQ.
     * POST /bff/publicar
     */
    @PostMapping("/publicar")
    public ResponseEntity<?> publicarInscripcion(@RequestBody Inscripcion inscripcion) {
        Inscripcion guardada = inscripcionServicio.crear(inscripcion);
        return ResponseEntity.ok(guardada);
    }

    /**
     * Endpoint consumidor: consulta los mensajes procesados desde la cola
     * y guardados en la tabla inscripciones_mensajes.
     * GET /bff/mensajes
     */
    @GetMapping("/mensajes")
    public ResponseEntity<List<InscripcionMensaje>> obtenerMensajes() {
        List<InscripcionMensaje> mensajes = inscripcionMensajeRepositorio.findAll();
        return ResponseEntity.ok(mensajes);
    }

    /**
     * GET /bff/estado/{id}
     * Consulta el estado de una inscripcion por su ID.
     */
    @GetMapping("/estado/{id}")
    public ResponseEntity<Inscripcion> estadoInscripcion(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionServicio.obtener(id));
    }
}
