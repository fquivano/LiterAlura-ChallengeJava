package com.alura.literatura.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ListaLibrosData(
        @JsonAlias("results") List<LibroData> resultados
){
}
