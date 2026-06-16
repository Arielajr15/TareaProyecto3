package org.example.tareaproyecto3.logic.mappers;

import org.example.tareaproyecto3.logic.models.dtos.ProductoDTO;
import org.example.tareaproyecto3.logic.models.entities.Categoria;
import org.example.tareaproyecto3.logic.models.entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    ProductoDTO productoToDTO(Producto producto);

    Producto productoDtoToProducto(ProductoDTO productoDTO);

    default Long map(Categoria value) {
        if (value == null) {
            return null;
        }

        return value.getId();
    }

    default Categoria map(Long value) {
        if (value == null) {
            return null;
        }

        Categoria categoria = new Categoria();
        categoria.setId(value);
        return categoria;
    }
}