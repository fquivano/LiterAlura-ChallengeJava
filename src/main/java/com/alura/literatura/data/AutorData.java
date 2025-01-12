package com.alura.literatura.data;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorData(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer fechaNacimiento,
        @JsonAlias("death_year") Integer fechaDeDefuncion
) {
}
