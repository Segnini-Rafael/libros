package com.alura.literalura.segnini.repository;

import com.alura.literalura.segnini.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Método para verificar si ya existe un libro por título y autor
    boolean existsByTituloAndAutor(String titulo, String autor);
    List<Libro> findByIdioma(String idioma);

    @Query("SELECT DISTINCT l.autor FROM Libro l")
    List<String> findDistinctAutores();

    @Query("SELECT DISTINCT l.autor FROM Libro l WHERE l.anioNacimiento <= :year AND (l.anioMuerte IS NULL OR l.anioMuerte > :year)")
    List<String> findAutoresVivosEnAnio(@Param("year") int year);
}
