package com.alura.literatura.models;

import com.alura.literatura.data.LibroData;
import com.alura.literatura.enums.Genero;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "libros")
public class LibroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "autor_id")
    private AutorEntity autor;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String lenguaje;
    private Integer numeroDescargas;

    public LibroEntity() {
    }

    public LibroEntity(LibroData libroData) {
        this.titulo = libroData.titulo();
        if (libroData.autores() != null && !libroData.autores().isEmpty()) {
            this.autor = new AutorEntity(libroData.autores().get(0));
        } else {
            this.autor = null; // o maneja el caso de que no haya autor
        }
        this.genero = validarGenero(libroData.genero());
        this.lenguaje = validarLenguaje(libroData.lenguajes());
        this.numeroDescargas = libroData.numeroDescargas();

    }

    private Genero validarGenero(List<String> generos) {
        if (generos == null || generos.isEmpty()) {
            return Genero.DESCONOCIDO;
        }
        return generos.stream()
                .filter(Objects::nonNull)
                .map(LibroEntity::filtroGenero)
                .filter(g -> !g.isEmpty())
                .map(Genero::fromString)
                .findFirst()
                .orElse(Genero.DESCONOCIDO);
    }

    private static String filtroGenero(String genero) {
        return genero.contains("--") ? genero.substring(0, genero.indexOf(" -- ")).trim() : genero;
    }


    private String validarLenguaje(List<String> lenguajes) {
        if (lenguajes == null || lenguajes.isEmpty()) {
            return "No listado";
        }
        return lenguajes.get(0);
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAutor(AutorEntity autor) {
        this.autor = autor;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public AutorEntity getAutor() {
        return autor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", genero=" + genero +
                ", lenguaje='" + lenguaje + '\'' +
                ", numeroDescargas=" + numeroDescargas +
                '}';
    }
}
