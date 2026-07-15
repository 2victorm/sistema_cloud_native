package com.duoc.sistemainscripcion.cursos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscripciones_mensajes")
public class InscripcionMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoInscripcion;
    private String nombreEstudiante;
    private String emailEstudiante;
    private String nombreCurso;
    private String instructor;
    private String estado;
    private LocalDate fechaInscripcion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoInscripcion() { return codigoInscripcion; }
    public void setCodigoInscripcion(String codigoInscripcion) { this.codigoInscripcion = codigoInscripcion; }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

    public String getEmailEstudiante() { return emailEstudiante; }
    public void setEmailEstudiante(String emailEstudiante) { this.emailEstudiante = emailEstudiante; }

    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
}
