package com.literalura.literalura.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AutorCatalogo {
    private final List<Author> autores = new ArrayList<>();

    public void agregarAutor(Author autor) {
        if (autor != null && autor.getName() != null && !autor.getName().isBlank() && autores.stream().noneMatch(a -> a.getName().equalsIgnoreCase(autor.getName()))) {
            autores.add(autor);
        }
    }

    public List<Author> getAutores() {
        return new ArrayList<>(autores);
    }

    public List<Author> buscarVivosEnAnio(int anio) {
        return autores.stream()
                .filter(a -> a.isAliveInYear(anio))
                .collect(Collectors.toList());
    }
}
