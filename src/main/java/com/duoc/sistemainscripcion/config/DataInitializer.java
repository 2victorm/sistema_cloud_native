package com.duoc.sistemainscripcion.config;

import com.duoc.sistemainscripcion.cursos.model.Inscripcion;
import com.duoc.sistemainscripcion.cursos.repository.InscripcionRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner cargarDatos(InscripcionRepositorio inscripcionRepositorio) {
        return args -> {
            Inscripcion i1 = new Inscripcion();
            i1.setCodigoInscripcion("INS-2024-001");
            i1.setNombreEstudiante("Ana Torres");
            i1.setEmailEstudiante("ana.torres@duoc.cl");
            i1.setNombreCurso("Desarrollo Cloud Native");
            i1.setInstructor("Ignacio Pastenet");
            i1.setEstado("ACTIVO");
            i1.setFechaInscripcion(LocalDate.now());
            inscripcionRepositorio.save(i1);

            Inscripcion i2 = new Inscripcion();
            i2.setCodigoInscripcion("INS-2024-002");
            i2.setNombreEstudiante("Carlos López");
            i2.setEmailEstudiante("carlos.lopez@duoc.cl");
            i2.setNombreCurso("Ingeniería de Software");
            i2.setInstructor("María González");
            i2.setEstado("PENDIENTE");
            i2.setFechaInscripcion(LocalDate.now());
            inscripcionRepositorio.save(i2);

            Inscripcion i3 = new Inscripcion();
            i3.setCodigoInscripcion("INS-2024-003");
            i3.setNombreEstudiante("Pedro Soto");
            i3.setEmailEstudiante("pedro.soto@duoc.cl");
            i3.setNombreCurso("Desarrollo Cloud Native");
            i3.setInstructor("Ignacio Pastenet");
            i3.setEstado("COMPLETADO");
            i3.setFechaInscripcion(LocalDate.now());
            inscripcionRepositorio.save(i3);

            System.out.println(">>> Inscripciones de prueba cargadas en H2");
        };
    }
}
