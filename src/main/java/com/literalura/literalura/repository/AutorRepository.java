package com.literalura.literalura.repository;

import com.literalura.literalura.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanOrAnioFallecimientoIsNull(int anio, int anio2);
}
