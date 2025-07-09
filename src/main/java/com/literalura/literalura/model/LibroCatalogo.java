package com.literalura.literalura.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibroCatalogo {
    private final List<Book> libros = new ArrayList<>();

    public void agregarLibro(Book libro) {
        libros.add(libro);
    }

    public List<Book> getLibros() {
        return new ArrayList<>(libros);
    }

    public List<Book> buscarPorIdioma(String idioma) {
        return libros.stream()
                .filter(l -> idioma.equalsIgnoreCase(l.getFirstLanguage()))
                .collect(Collectors.toList());
    }
}
