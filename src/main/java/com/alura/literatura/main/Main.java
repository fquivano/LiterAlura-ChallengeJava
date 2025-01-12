package com.alura.literatura.main;

import com.alura.literatura.data.ListaLibrosData;
import com.alura.literatura.models.AutorEntity;
import com.alura.literatura.models.LibroEntity;
import com.alura.literatura.repository.IAutorRepository;
import com.alura.literatura.repository.ILibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private Scanner teclado = new Scanner(System.in);

    private ConvierteDatos conversor = new ConvierteDatos();
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE =  "https://gutendex.com/books/?search=";
    private IAutorRepository autorRepository;
    private ILibroRepository libroRepository;

    public Main(IAutorRepository autorRepository, ILibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {
        int eleccion;
        do {
            System.out.println("=".repeat(10));
            System.out.println("""
                 Elige una opción a consultar 
                ----------------------------------
                1. Buscar libro por su titulo.
                2. Listar libros registrados.
                3. Listar autores registrados.
                4. Listar autores vivos por año.
                5. Listar por idiomas.
                0. Salir.
                
                """);
            System.out.println("=".repeat(10));
            System.out.print(" ");
            while (!teclado.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido.");
                teclado.next(); // Consumir la entrada inválida
                System.out.print("> ");
            }
            eleccion = teclado.nextInt();
            teclado.nextLine();
            if (eleccion >= 0 && eleccion <= 5) { // Verifica si la opción es válida
                switch (eleccion) {
                    case 1 -> buscarLibroPorNombre();
                    case 2 -> consultarRegistrados();
                    case 3 -> consultarAutoresRegistrados();
                    case 4 -> consultarAutoresPorFecha();
                    case 5 -> consultarIdiomaLibro();
                    case 0 -> {
                        System.out.println("Cerrando la aplicación.");
                        System.exit(0);
                    }
                }
            } else {
                System.out.println("Opción inválida, por favor elige una opción entre 0 y 5.");
            }
        } while (eleccion != 0);
    }


    private void buscarLibroPorNombre() {
        Optional.ofNullable(getDatosLibro())
                .ifPresentOrElse(libro -> {
                            try {
                                if (libroRepository.existsByTitulo(libro.getTitulo())) {
                                    System.out.println("El libro ya está en la base de datos!");
                                } else {
                                    libroRepository.saveAndFlush(libro);
                                    System.out.println(libro);
                                }
                            } catch (InvalidDataAccessApiUsageException e) {
                                System.out.println("No se puede guardar el libro buscado!");
                            }
                        },
                        () -> System.out.println("Libro no Encontrado. Intente Nuevamente"));

    }

    private LibroEntity getDatosLibro() {
        System.out.println("Ingrese el Nombre del Libro a Encontrar: ");
        String nombreLibro = teclado.nextLine();
        String encodedNombreLibro = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);

        String json = consumoApi.obtenerDatos(URL_BASE + encodedNombreLibro);
        ListaLibrosData datos = conversor.obtenerDatos(json, ListaLibrosData.class);

        return Optional.ofNullable(datos)
                .map(ListaLibrosData::resultados)
                .filter(libros -> !libros.isEmpty())
                .map(libros -> {
                    // Crear un libro a partir de los datos obtenidos de la API
                    LibroEntity libro = new LibroEntity(libros.get(0));

                    // Obtener o crear el autor
                    String nombreAutor = libros.get(0).autores().get(0).nombre();
                    AutorEntity autor = autorRepository.findByNombre(nombreAutor);

                    if (autor == null) {
                        autor = new AutorEntity(nombreAutor, libros.get(0).autores().get(0).fechaNacimiento(), libros.get(0).autores().get(0).fechaDeDefuncion());
                    } else {
                        autor = autorRepository.save(autor);
                    }
                    libro.setAutor(autor);
                    return libro;
                })
                .orElseGet(() -> {
                    System.out.println("No se encontraron resultados en la búsqueda.");
                    return null;
                });
    }

    private void consultarIdiomaLibro() {
        String idioma;
        List<String> idiomasValidos = List.of("ES", "EN", "IT"); // Idiomas válidos
        boolean idiomaValido = false;

        do {
            System.out.println("Ingrese Idioma a buscar: \n");
            System.out.println("_".repeat(50));
            System.out.println("  Opción - ES : Libros en español. ");
            System.out.println("  Opción - EN : Libros en inglés. ");
            System.out.println("  Opción - IT : Libros en italiano.  ");
            System.out.println("-".repeat(50) + "\n");

            idioma = teclado.nextLine().toUpperCase(); // Convertir a mayúsculas para facilitar la comparación

            if (idiomasValidos.contains(idioma)) {
                idiomaValido = true;
                List<LibroEntity> librosPorIdioma = libroRepository.findByLenguaje(idioma.toLowerCase());

                if (librosPorIdioma.isEmpty()) {
                    System.out.println("No se encontraron libros en el idioma seleccionado.");
                } else {
                    System.out.println("Todos los libros encontrados según su idioma (" + idioma + "):");
                    for (LibroEntity libro : librosPorIdioma) {
                        System.out.println(libro.toString());
                    }
                }
            } else {
                System.out.println("Opción inválida. Por favor, elija uno de los idiomas válidos (ES, EN, IT).");
            }
        } while (!idiomaValido);
    }


    private void consultarAutoresPorFecha() {
        int anioConsulta = obtenerAnioValido();
        List<AutorEntity> autoresDeEseAnio = obtenerAutoresVivosEnAnio(anioConsulta);

        if (autoresDeEseAnio == null || autoresDeEseAnio.isEmpty()) {
            System.out.println("No se encontraron autores que estuvieran vivos en el año " + anioConsulta + ".");
        } else {
            mostrarAutoresVivos(autoresDeEseAnio, anioConsulta);
        }
    }

    private int obtenerAnioValido() {
        int anioConsulta = -1;
        while (anioConsulta <= 0) {
            System.out.print("Indica el año para consultar qué autores estaban vivos: ");

            if (teclado.hasNextInt()) {
                anioConsulta = teclado.nextInt();
                teclado.nextLine();
                if (anioConsulta <= 0) {
                    System.out.println("El año debe ser un valor positivo. Por favor, ingresa un año válido.");
                }
            } else {
                System.out.println("Entrada no válida. Debes ingresar un número de año válido (sin letras).");
                teclado.nextLine(); // Limpiar el buffer en caso de que el usuario ingrese algo no numérico
            }
        }
        return anioConsulta;
    }

    private List<AutorEntity> obtenerAutoresVivosEnAnio(int anioConsulta) {
        return autorRepository.findByFechaNacimientoLessThanOrFechaDeDefuncionGreaterThanEqual(anioConsulta, anioConsulta);
    }

    private void mostrarAutoresVivos(List<AutorEntity> autoresVivos, int anioConsulta) {
        Set<String> autoresUnicos = new HashSet<>(); // Evitar repetir autores

        for (AutorEntity autor : autoresVivos) {
            if (esAutorVivoEnAnio(autor, anioConsulta) && autoresUnicos.add(autor.getNombre())) {
                System.out.println("Autor: " + autor.getNombre());
            }
        }
    }

    private boolean esAutorVivoEnAnio(AutorEntity autor, int anioConsulta) {
        // Verifica si el autor estuvo vivo en el año dado
        return autor.getFechaNacimiento() != null &&
                autor.getFechaDeDefuncion() != null &&
                autor.getFechaNacimiento() <= anioConsulta &&
                autor.getFechaDeDefuncion() >= anioConsulta;
    }

    private void consultarAutoresRegistrados() {
        List<AutorEntity> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No se encontraron los Autores en la base de datos. \n");
        } else {
            System.out.println("Lista de Autores encontrados en la base de datos: \n");
            Set<String> autoresUnicos = new HashSet<>();
            for (AutorEntity autor : autores) {

                if (autoresUnicos.add(autor.getNombre())){
                    System.out.println(autor.getNombre());
                }
            }
        }
    }

    private void consultarRegistrados() {
        List<LibroEntity> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos.");
        } else {
            System.out.println("Libros encontrados en la base de datos:");
            for (LibroEntity libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }



}
