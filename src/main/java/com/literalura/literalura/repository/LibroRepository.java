package com.literalura.literalura.repository;

import com.literalura.literalura.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    long countByIdioma(String idioma);
}
