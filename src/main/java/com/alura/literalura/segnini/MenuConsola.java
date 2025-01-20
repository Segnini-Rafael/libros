package com.alura.literalura.segnini;

import com.alura.literalura.segnini.model.Libro;
import com.alura.literalura.segnini.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MenuConsola implements CommandLineRunner {

    @Autowired
    private LibroService libroService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1; // Inicializamos con -1 para entrar al bucle do-while

        do {
            mostrarMenu(); // Mostrar el menú

            // Validar si el usuario ingresó un número
            if (!scanner.hasNextInt()) {
                System.out.println("⚠️ Opción inválida. Por favor, ingrese un número del menú.");
                scanner.next(); // Limpiar entrada no válida
                continue; // Reiniciar el bucle
            }

            opcion = scanner.nextInt(); // Leer la opción ingresada por el usuario
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el título del libro a buscar: ");
                    String titulo = scanner.nextLine();
                    Libro libro = libroService.buscarEnGutendex(titulo);
                    if (libro != null) {
                        libroService.guardarLibro(libro);
                    } else {
                        System.out.println("⚠️ No se encontró el libro con el título especificado.");
                    }
                    break;

                case 2:
                    List<Libro> libros = libroService.listarLibros();
                    if (libros.isEmpty()) {
                        System.out.println("⚠️ No hay libros registrados.");
                    } else {
                        System.out.println("Libros registrados:");
                        libros.forEach(libroReg -> System.out.println(formatLibro(libroReg)));
                    }
                    break;

                case 3:
                    List<String> autores = libroService.listarAutores();
                    if (autores.isEmpty()) {
                        System.out.println("⚠️ No hay autores registrados.");
                    } else {
                        System.out.println("Autores registrados:");
                        autores.forEach(System.out::println);
                    }
                    break;

                case 4:
                    System.out.print("Ingrese el año para buscar autores vivos: ");
                    int year = scanner.nextInt();
                    List<String> autoresVivos = libroService.listarAutoresVivos(year);
                    if (autoresVivos.isEmpty()) {
                        System.out.println("⚠️ No se encontraron autores vivos para el año especificado.");
                    } else {
                        System.out.println("Autores vivos en el año " + year + ":");
                        autoresVivos.forEach(System.out::println);
                    }
                    break;

                case 5:
                    mostrarInstruccionesIdioma(); // Mostrar ejemplos de idiomas
                    System.out.print("Ingrese el idioma para filtrar libros: ");
                    String idioma = scanner.nextLine().toLowerCase(); // Convertir a minúsculas para evitar problemas
                    List<Libro> librosPorIdioma = libroService.listarLibrosPorIdioma(idioma);
                    if (librosPorIdioma.isEmpty()) {
                        System.out.println("⚠️ No se encontraron libros en el idioma especificado.");
                    } else {
                        System.out.println("Libros en idioma " + idioma + ":");
                        librosPorIdioma.forEach(libroIdioma -> System.out.println(formatLibro(libroIdioma)));
                    }
                    break;

                case 0:
                    System.out.println("¡Saliendo del sistema!");
                    break;

                default:
                    System.out.println("⚠️ Opción inválida. Por favor, elija una opción del menú.");
            }

        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("==================================");
        System.out.println("📚 Menú Principal:");
        System.out.println("1- Buscar libro por título");
        System.out.println("2- Listar libros registrados");
        System.out.println("3- Listar autores registrados");
        System.out.println("4- Listar autores vivos en un determinado año");
        System.out.println("5- Listar libros por idioma");
        System.out.println("0- Salir");
        System.out.println("==================================");
        System.out.print("Ingrese su opción: ");
    }

    private void mostrarInstruccionesIdioma() {
        System.out.println("\n📘 Instrucciones para ingresar idioma:");
        System.out.println("Español: es, ES, español, spanish");
        System.out.println("Inglés: en, EN, english, inglés");
        System.out.println("Portugués: pt, PT, portuguese, portugués");
        System.out.println("Italiano: it, IT, italian, italiano");
        System.out.println("Alemán: de, DE, german, alemán");
        System.out.println("Ruso: ru, RU, russian, ruso");
        System.out.println("----------------------------------");
    }

    private String formatLibro(Libro libro) {
        return String.format("📖 Título: %s\n✍️ Autor: %s\n🌐 Idioma: %s\n⬇️ Descargas: %d\n---------------------------",
                libro.getTitulo(), libro.getAutor(), libro.getIdioma(), libro.getDescargas());
    }
}