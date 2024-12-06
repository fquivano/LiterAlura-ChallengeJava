package com.aluracursos.LiterAlura_ClallengeJava.principal;

import com.aluracursos.LiterAlura_ClallengeJava.service.ConsumoAPI;

import java.util.Scanner;

public class principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
}
