package com.alura.literalura.segnini.service;

import com.alura.literalura.segnini.model.Libro;
import com.alura.literalura.segnini.repository.LibroRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public Optional<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findAll()
                .stream()
                .filter(libro -> libro.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    public void guardarLibro(Libro libro) {
        if (!libroRepository.existsByTituloAndAutor(libro.getTitulo(), libro.getAutor())) {
            libroRepository.save(libro);
            System.out.println("✅ Libro guardado exitosamente:");
            mostrarDetallesLibro(libro);
        } else {
            System.out.println("⚠️ El libro ya existe en la base de datos:");
            mostrarDetallesLibro(libro);
        }
    }

    public Libro buscarEnGutendex(String titulo) {
        try {
            HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://gutendex.com/books?search=" + titulo))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\n🔍 Respuesta de Gutendex recibida:");

            if (response.statusCode() != 200) {
                System.out.println("❌ Error al conectar con Gutendex. Código de estado: " + response.statusCode());
                return null;
            }

            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray results = jsonResponse.getJSONArray("results");

            if (results.length() == 0) {
                System.out.println("⚠️ No se encontraron libros con el título: " + titulo);
                return null;
            }

            JSONObject firstBook = results.getJSONObject(0);
            JSONArray authors = firstBook.getJSONArray("authors");

            // Crear el objeto libro
            Libro libro = new Libro();
            libro.setTitulo(firstBook.getString("title"));
            libro.setIdioma(firstBook.getJSONArray("languages").getString(0));
            libro.setDescargas(firstBook.getInt("download_count"));

            if (authors.length() > 0) {
                JSONObject author = authors.getJSONObject(0);
                libro.setAutor(author.getString("name"));

                // Intentar extraer los años de nacimiento y muerte si están disponibles
                if (author.has("birth_year")) {
                    libro.setAnioNacimiento(author.getInt("birth_year"));
                }
                if (author.has("death_year")) {
                    libro.setAnioMuerte(author.getInt("death_year"));
                }
            } else {
                libro.setAutor("Autor desconocido");
            }

            return libro;

        } catch (Exception e) {
            System.err.println("❌ Error al buscar en Gutendex: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public void mostrarDetallesLibro(Libro libro) {
        System.out.println("------------------------------");
        System.out.println("📚 Título: " + libro.getTitulo());
        System.out.println("✍️  Autor: " + libro.getAutor());
        System.out.println("🌐 Idioma: " + libro.getIdioma());
        System.out.println("⬇️  Descargas: " + libro.getDescargas());
        System.out.println("------------------------------");
    }

    public List<String> listarAutores() {
        return libroRepository.findAll()
                .stream()
                .map(Libro::getAutor)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> listarAutoresVivos(int year) {
        return libroRepository.findAll() // Traemos todos los libros de la base de datos
                .stream() // Convertimos la lista a un stream para poder procesar
                .filter(libro -> libro.getAnioNacimiento() != null &&
                        (libro.getAnioMuerte() == null || libro.getAnioMuerte() > year)) // Condición para filtrar autores vivos
                .map(Libro::getAutor) // Extraemos solo el autor
                .distinct() // Eliminamos duplicados
                .collect(Collectors.toList()); // Convertimos el resultado final a una lista
    }


    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findAll()
                .stream()
                .filter(libro -> idioma.equalsIgnoreCase(libro.getIdioma()))
                .collect(Collectors.toList());
    }


}
