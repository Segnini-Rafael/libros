package com.alura.literalura.segnini.controller;
import com.alura.literalura.segnini.model.Libro;
import com.alura.literalura.segnini.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<Libro> listarLibros() {
        return libroService.listarLibros();
    }

    @GetMapping("/{titulo}")
    public Libro buscarPorTitulo(@PathVariable String titulo) {
        return libroService.buscarPorTitulo(titulo).orElse(null);
    }

    @PostMapping
    public void guardarLibro(@RequestBody Libro libro) {
        libroService.guardarLibro(libro);
    }

    @GetMapping("/buscar/{titulo}")
    public Libro buscarYGuardarLibro(@PathVariable String titulo) {
        Libro libro = libroService.buscarEnGutendex(titulo);
        if (libro != null) {
            libroService.guardarLibro(libro);
            return libro;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado en Gutendex");
    }
}