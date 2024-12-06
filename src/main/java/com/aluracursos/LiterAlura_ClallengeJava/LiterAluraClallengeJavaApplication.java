package com.aluracursos.LiterAlura_ClallengeJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraClallengeJavaApplication {

	public static void main(String[] args) {
	int opcion = 0;

		//SpringApplication.run(LiterAluraClallengeJavaApplication.class, args);
		String menu ="""
				**********************************
				Elija la opcion a traves de su numero:
				1 - Buscar libro por titulo
				2 - Listar libros registrados
				3 - Listar autores registrados
				4 - Listar autores vivos en un determinado año
				5 - Listar libros por idioma
				0 - Salir
				""";

		Scanner teclado = new Scanner(System.in);
		System.out.println("Digita la opracion a realizar");

		while(opcion != 9){
			System.out.println(menu);
			opcion = teclado.nextInt();

			switch (opcion){
				case 1:
					System.out.println("Desarrollando busqueda por nombre");
					break;
			}

		}





	}

}
