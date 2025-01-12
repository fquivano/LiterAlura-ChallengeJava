package com.alura.literatura.data;

import com.alura.literatura.models.AutorEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroData (
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorData> autores,
        @JsonAlias("subjects")  List<String> genero,
        @JsonAlias("languages") List<String> lenguajes,
        @JsonAlias("download_count") Integer numeroDescargas
){
}
