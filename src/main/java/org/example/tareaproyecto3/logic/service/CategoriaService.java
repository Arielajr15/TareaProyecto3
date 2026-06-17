package org.example.tareaproyecto3.logic.service;

import org.example.tareaproyecto3.logic.models.dtos.CategoriaDTO;
import org.example.tareaproyecto3.logic.models.entities.Categoria;
import org.example.tareaproyecto3.logic.repository.CategoriaRepository;
import org.example.tareaproyecto3.logic.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaService(
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository
    ) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public ResponseEntity<CategoriaDTO> crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertirCategoriaADTO(categoriaGuardada));
    }

    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        List<CategoriaDTO> categorias = categoriaRepository.findAll()
                .stream()
                .map(this::convertirCategoriaADTO)
                .toList();

        return ResponseEntity.ok(categorias);
    }

    public ResponseEntity<CategoriaDTO> buscarCategoriaPorId(Long id) {
        Optional<Categoria> unaCategoria = categoriaRepository.findById(id);

        if (unaCategoria.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertirCategoriaADTO(unaCategoria.get()));
    }

    public ResponseEntity<CategoriaDTO> actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Optional<Categoria> unaCategoria = categoriaRepository.findById(id);

        if (unaCategoria.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Categoria categoria = unaCategoria.get();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria categoriaActualizada = categoriaRepository.save(categoria);

        return ResponseEntity.ok(convertirCategoriaADTO(categoriaActualizada));
    }

    public ResponseEntity<String> eliminarCategoria(Long id) {
        Optional<Categoria> unaCategoria = categoriaRepository.findById(id);

        if (unaCategoria.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Esa categoría no existe");
        }

        long productosAsociados = productoRepository.countByCategoriaId(id);

        if (productosAsociados > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se puede eliminar la categoría porque tiene productos asociados");
        }

        categoriaRepository.delete(unaCategoria.get());

        return ResponseEntity.ok("Categoría eliminada correctamente");
    }

    private CategoriaDTO convertirCategoriaADTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());

        return categoriaDTO;
    }
}