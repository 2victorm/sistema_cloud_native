package com.duoc.sistemainscripcion.inscripciones.model;

import java.util.List;

import com.duoc.sistemainscripcion.cursos.model.Curso;

import jakarta.persistence.*;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreEstudiante;

    @ManyToMany
    @JoinTable(name = "inscripcion_cursos", joinColumns = @JoinColumn(name = "inscripcion_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private List<Curso> cursos;

    private double totalPagar;

    public Inscripcion() {
    }

    public Long getId() {
        return id;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }
}