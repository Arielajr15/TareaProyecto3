package org.example.tareaproyecto3.logic.controller;


import org.example.tareaproyecto3.logic.models.dtos.ProductoDTO;
import org.example.tareaproyecto3.logic.models.entities.Producto;
import org.example.tareaproyecto3.logic.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public Producto crearProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.crearProducto(productoDTO);
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductoDTO> buscarProductoPorId(@PathVariable Long id) {
        return productoService.buscarProductoPorId(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public ResponseEntity<ProductoDTO> actualizarProducto(
            @PathVariable Long id,
            @RequestBody ProductoDTO productoDTO
    ) {
        return productoService.actualizarProducto(id, productoDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        return productoService.eliminarProducto(id);
    }
}
