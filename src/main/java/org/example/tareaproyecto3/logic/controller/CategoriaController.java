package org.example.tareaproyecto3.logic.controller;


import org.example.tareaproyecto3.logic.models.dtos.CategoriaDTO;
import org.example.tareaproyecto3.logic.models.entities.Categoria;
import org.example.tareaproyecto3.logic.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public Categoria crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.crearCategoria(categoriaDTO);
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CategoriaDTO> buscarCategoriaPorId(@PathVariable Long id) {
        return categoriaService.buscarCategoriaPorId(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaDTO categoriaDTO
    ) {
        return categoriaService.actualizarCategoria(id, categoriaDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id) {
        return categoriaService.eliminarCategoria(id);
    }
}