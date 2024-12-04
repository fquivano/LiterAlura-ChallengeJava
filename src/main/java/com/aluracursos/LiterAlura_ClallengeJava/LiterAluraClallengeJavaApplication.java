package com.aluracursos.LiterAlura_ClallengeJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraClallengeJavaApplication {

	public static void main(String[] args) {

		//SpringApplication.run(LiterAluraClallengeJavaApplication.class, args);
		System.out.println("""
				Elija la opcion a traves de su numero:
				1 - Buscar libro por titulo
				2 - Listar libros registrados
				3 - Listar autores registrados
				4 - Listar autores vivos en un determinado año
				5 - Listar libros por idioma
				0 - Salir
				""");
	}

}
