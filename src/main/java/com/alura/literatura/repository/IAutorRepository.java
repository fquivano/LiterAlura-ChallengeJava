package com.alura.literatura.repository;

import com.alura.literatura.models.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAutorRepository extends JpaRepository<AutorEntity, Long> {


    List<AutorEntity> findAll();
    AutorEntity findByNombre(String nombre);

    List<AutorEntity> findByFechaNacimientoLessThanOrFechaDeDefuncionGreaterThanEqual(int anioInicio, int anioFin);
/*
    Optional<AutorEntity> findFirstByNombreContainsIgnoreCase(String escritor);

 */

}
