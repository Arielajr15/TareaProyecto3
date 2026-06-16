package org.example.tareaproyecto3.logic.mappers;

import org.example.tareaproyecto3.logic.models.dtos.CategoriaDTO;
import org.example.tareaproyecto3.logic.models.entities.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    CategoriaDTO categoriaToDTO(Categoria categoria);

    @Mapping(target = "productos", ignore = true)
    Categoria categoriaDtoToCategoria(CategoriaDTO categoriaDTO);
}