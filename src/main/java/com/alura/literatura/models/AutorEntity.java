package com.alura.literatura.models;

import com.alura.literatura.data.AutorData;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "autores")
public class AutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaDeDefuncion;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LibroEntity> libros;

    public AutorEntity() {
    }


    public AutorEntity(String nombre, Integer fechaNacimiento, Integer fechaDeDefuncion) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaDeDefuncion = fechaDeDefuncion;
    }

    public AutorEntity(AutorData autorData) {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLibros(List<LibroEntity> libros) {
        this.libros = libros;
    }


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaDeDefuncion() {
        return fechaDeDefuncion;
    }

    public void setFechaDeDefuncion(Integer fechaDeDefuncion) {
        this.fechaDeDefuncion = fechaDeDefuncion;
    }

    public List<LibroEntity> getLibros() {
        return libros;
    }

    @Override
    public String toString() {
        return
                "nombre = '" + nombre + '\'' +
                ", fechaNacimiento = " + fechaNacimiento +
                ", fechaDeDefuncion = " + fechaDeDefuncion;
    }
}
