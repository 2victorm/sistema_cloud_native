package com.duoc.sistemainscripcion.cursos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String instructor;

    private int duracionHoras;

    private double costo;

    public Curso() {
    }

    public Curso(String nombre, String instructor, int duracionHoras, double costo) {
        this.nombre = nombre;
        this.instructor = instructor;
        this.duracionHoras = duracionHoras;
        this.costo = costo;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getDuracionHoras() {
        return duracionHoras;
    }

    public double getCosto() {
        return costo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setDuracionHoras(int duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}