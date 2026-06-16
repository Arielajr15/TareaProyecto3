package org.example.tareaproyecto3.logic.repository;

import org.example.tareaproyecto3.logic.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    long countByCategoriaId(Long categoriaId);

}