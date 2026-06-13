package com.duoc.sistemainscripcion.resumenes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.sistemainscripcion.inscripciones.model.Inscripcion;
import com.duoc.sistemainscripcion.inscripciones.repository.InscripcionRepositorio;

@RestController
@RequestMapping("/resumenes")
public class S3Controlador {

    @Autowired
    private S3Servicio s3Servicio;

    @Autowired
    private InscripcionRepositorio inscripcionRepository;

    @Autowired
    private ResumenServicio resumenServicio;

    @Value("${aws.bucketName}")
    private String bucket;

    // POST /resumenes/{id}/subir
    // Genera el resumen .txt de la inscripción y lo sube a S3
    // El archivo queda en: resumenes/{id}/resumen_{id}.txt
    @PostMapping("/{id}/subir")
    public ResponseEntity<String> subirResumen(@PathVariable Long id) {
        try {
            Inscripcion inscripcion = inscripcionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Inscripción no encontrada: " + id));

            File archivo = resumenServicio.generarResumen(inscripcion);
            String resultado = s3Servicio.subirArchivo(bucket, archivo, id);
            archivo.delete();

            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // GET /resumenes/{id}/descargar
    // Descarga el resumen desde S3 como archivo al computador
    @GetMapping("/{id}/descargar")
    public ResponseEntity<ByteArrayResource> descargarResumen(@PathVariable Long id) {
        try {
            String key = "resumenes/" + id + "/resumen_" + id + ".txt";
            byte[] contenido = s3Servicio.descargarArchivo(bucket, key);

            ByteArrayResource resource = new ByteArrayResource(contenido);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"resumen_" + id + ".txt\"")
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(contenido.length)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // PUT /resumenes/{id}/modificar
    // Regenera el resumen y sobreescribe el archivo existente en S3
    @PutMapping("/{id}/modificar")
    public ResponseEntity<String> modificarResumen(@PathVariable Long id) {
        try {
            Inscripcion inscripcion = inscripcionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Inscripción no encontrada: " + id));

            File archivo = resumenServicio.generarResumen(inscripcion);

            // subirArchivo usa la misma key → sobreescribe el archivo existente
            String resultado = s3Servicio.subirArchivo(bucket, archivo, id);
            archivo.delete();

            return ResponseEntity.ok("Resumen actualizado. " + resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // DELETE /resumenes/{id}/eliminar
    // Borra el archivo de resumen desde S3
    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<String> eliminarResumen(@PathVariable Long id) {
        try {
            String key = "resumenes/" + id + "/resumen_" + id + ".txt";
            s3Servicio.eliminarArchivo(bucket, key);
            return ResponseEntity.ok("Resumen eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // GET /resumenes/listar
    // Lista todos los archivos en el bucket (útil para verificar)
    @GetMapping("/listar")
    public ResponseEntity<List<String>> listar() {
        return ResponseEntity.ok(s3Servicio.listarArchivos(bucket));
    }
}
