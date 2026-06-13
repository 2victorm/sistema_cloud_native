package com.duoc.sistemainscripcion.config;

import com.duoc.sistemainscripcion.cursos.model.Curso;
import com.duoc.sistemainscripcion.cursos.repository.CursoRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner cargarDatos(CursoRepositorio cursoRepositorio) {
        return args -> {
            if (cursoRepositorio.count() == 0) {
                cursoRepositorio.save(new Curso("Desarrollo Cloud Native", "Carlos Valverde", 40, 150000));
                cursoRepositorio.save(new Curso("Backend con Spring Boot", "Ana López", 30, 120000));
                cursoRepositorio.save(new Curso("Seguridad en Aplicaciones", "Pedro Soto", 20, 90000));
                cursoRepositorio.save(new Curso("DevOps y CI/CD", "Camila Rojas", 25, 100000));
                System.out.println(">>> Cursos de prueba cargados en H2");
            }
        };
    }
}
