package com.alura.literatura.repository;

import com.alura.literatura.models.LibroEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILibroRepository extends JpaRepository<LibroEntity, Long> {

    boolean existsByTitulo(String titulo);

    List<LibroEntity> findByLenguaje(String idioma);

}
