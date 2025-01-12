# literAlura
# Proyecto de Búsqueda y Registro de Libros

Este proyecto es una aplicación Java para la búsqueda de libros mediante una API y la gestión de un registro local de libros y autores. Los usuarios pueden interactuar con la aplicación a través de un menú en la consola para buscar libros, listar libros y autores registrados, y realizar consultas específicas.

## Funcionalidades

El menú principal de la aplicación ofrece las siguientes opciones:

1. **Buscar libro por su título**: Permite al usuario buscar un libro introduciendo el título. La búsqueda se realiza mediante la API en línea `https://gutendex.com/books/`.
2. **Listar libros registrados**: Muestra todos los libros que han sido registrados en la base de datos local.
3. **Listar autores registrados**: Muestra todos los autores que han sido registrados en la base de datos local.
4. **Listar autores vivos por año**: Filtra y lista los autores que están vivos según un año específico.
5. **Listar por idiomas**: Permite al usuario listar libros filtrándolos por idiomas específicos.
6. **Salir**: Cierra la aplicación.

## Requisitos

- **Java 17 o superior**
- **Maven** (para gestionar dependencias)
- **Internet** (para realizar búsquedas de libros en línea a través de la API)
- **Acceso a la API** de Gutendex (`https://gutendex.com/books/?search=`)

## Configuración

1. **Clona el repositorio**:

   ```bash
   git clone https://github.com/EdMass/lierAlura.git
   cd lierAlura

