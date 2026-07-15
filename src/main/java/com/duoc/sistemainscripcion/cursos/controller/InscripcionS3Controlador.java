package com.duoc.sistemainscripcion.cursos.controller;

import com.duoc.sistemainscripcion.cursos.model.Inscripcion;
import com.duoc.sistemainscripcion.cursos.service.InscripcionServicio;
import com.duoc.sistemainscripcion.s3.S3Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionS3Controlador {

    @Autowired
    private S3Servicio s3Servicio;

    @Autowired
    private InscripcionServicio inscripcionServicio;

    @Value("${aws.bucketName}")
    private String bucket;

    // POST /inscripciones/{id}/subir
    @PostMapping("/{id}/subir")
    public ResponseEntity<String> subirInscripcion(@PathVariable Long id) {
        try {
            Inscripcion insc = inscripcionServicio.obtener(id);
            File archivo = File.createTempFile("inscripcion_" + id, ".txt");
            try (FileWriter fw = new FileWriter(archivo)) {
                fw.write("=== COMPROBANTE DE INSCRIPCION ===\n");
                fw.write("Codigo: " + insc.getCodigoInscripcion() + "\n");
                fw.write("Estudiante: " + insc.getNombreEstudiante() + "\n");
                fw.write("Email: " + insc.getEmailEstudiante() + "\n");
                fw.write("Curso: " + insc.getNombreCurso() + "\n");
                fw.write("Instructor: " + insc.getInstructor() + "\n");
                fw.write("Estado: " + insc.getEstado() + "\n");
                fw.write("Fecha: " + insc.getFechaInscripcion() + "\n");
            }
            String key = "inscripciones/" + id + "/inscripcion_" + id + ".txt";
            s3Servicio.subirArchivo(bucket, archivo, id);
            archivo.delete();
            return ResponseEntity.ok("Comprobante subido a S3: " + key);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // GET /inscripciones/{id}/descargar
    @GetMapping("/{id}/descargar")
    public ResponseEntity<ByteArrayResource> descargarInscripcion(@PathVariable Long id) {
        try {
            String key = "inscripciones/" + id + "/inscripcion_" + id + ".txt";
            byte[] contenido = s3Servicio.descargarArchivo(bucket, key);
            ByteArrayResource resource = new ByteArrayResource(contenido);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"inscripcion_" + id + ".txt\"")
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(contenido.length)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // DELETE /inscripciones/{id}/eliminar-archivo
    @DeleteMapping("/{id}/eliminar-archivo")
    public ResponseEntity<String> eliminarArchivo(@PathVariable Long id) {
        try {
            String key = "inscripciones/" + id + "/inscripcion_" + id + ".txt";
            s3Servicio.eliminarArchivo(bucket, key);
            return ResponseEntity.ok("Archivo eliminado de S3 correctamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // GET /inscripciones/listar-archivos
    @GetMapping("/listar-archivos")
    public ResponseEntity<?> listarArchivos() {
        return ResponseEntity.ok(s3Servicio.listarArchivos(bucket));
    }
}
