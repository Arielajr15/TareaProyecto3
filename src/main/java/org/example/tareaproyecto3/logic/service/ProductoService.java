package org.example.tareaproyecto3.logic.service;

import org.example.tareaproyecto3.logic.models.dtos.ProductoDTO;
import org.example.tareaproyecto3.logic.models.entities.Categoria;
import org.example.tareaproyecto3.logic.models.entities.Producto;
import org.example.tareaproyecto3.logic.repository.CategoriaRepository;
import org.example.tareaproyecto3.logic.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public ResponseEntity<?> crearProducto(ProductoDTO productoDTO) {
        Optional<Categoria> categoria = categoriaRepository.findById(productoDTO.getCategoria());

        if (categoria.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Esa categoría no existe");
        }

        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCantidadStock(productoDTO.getCantidadStock());
        producto.setCategoria(categoria.get());

        Producto productoGuardado = productoRepository.save(producto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoGuardado);
    }

    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productos = productoRepository.findAll()
                .stream()
                .map(this::convertirProductoADTO)
                .toList();

        return ResponseEntity.ok(productos);
    }

    public ResponseEntity<ProductoDTO> buscarProductoPorId(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);

        if (producto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertirProductoADTO(producto.get()));
    }

    public ResponseEntity<?> actualizarProducto(Long id, ProductoDTO productoDTO) {
        Optional<Producto> productoEncontrado = productoRepository.findById(id);

        if (productoEncontrado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ese producto no existe");
        }

        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(productoDTO.getCategoria());

        if (categoriaEncontrada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Esa categoría no existe");
        }

        Producto producto = productoEncontrado.get();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCantidadStock(productoDTO.getCantidadStock());
        producto.setCategoria(categoriaEncontrada.get());

        Producto productoActualizado = productoRepository.save(producto);

        return ResponseEntity.ok(productoActualizado);
    }

    public ResponseEntity<String> eliminarProducto(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);

        if (producto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ese producto no existe");
        }

        productoRepository.delete(producto.get());

        return ResponseEntity.ok("Producto eliminado correctamente");
    }

    private ProductoDTO convertirProductoADTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();

        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setCantidadStock(producto.getCantidadStock());

        if (producto.getCategoria() != null) {
            productoDTO.setCategoria(producto.getCategoria().getId());
        }

        return productoDTO;
    }
}