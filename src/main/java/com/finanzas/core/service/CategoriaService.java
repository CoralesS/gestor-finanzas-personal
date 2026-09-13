package com.finanzas.core.service;

import com.finanzas.core.domain.Categoria;
import com.finanzas.core.dto.CategoriaEntradaDTO;
import com.finanzas.core.dto.CategoriaSalidaDTO;
import com.finanzas.core.repository.CategoriaRepository;
import com.finanzas.core.repository.MovimientoRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final MovimientoRepository movimientoRepository;

    // Constructor
    public CategoriaService(CategoriaRepository catRepo,  MovimientoRepository movRepo) {
        this.categoriaRepository = catRepo;
        this.movimientoRepository = movRepo;
    }

    // Métodos
    public void crearCategoria(CategoriaEntradaDTO dto) throws IllegalArgumentException {
        if(dto.getNombre() == null) {
            throw new IllegalArgumentException("El nombre del categoria no puede ser nulo");
        }

        Categoria catEntidad = new Categoria();
        catEntidad.setNombre(dto.getNombre());
        catEntidad.setDescripcion(dto.getDescripcion());
        categoriaRepository.guardar(catEntidad);

    }

    public List<CategoriaSalidaDTO> obtenerCategorias() {
        List<CategoriaSalidaDTO> categoriasDTO = new ArrayList<>();
        // Obtención de lista de categorias
        List<Categoria> listaCategorias = categoriaRepository.obtenerCategorias();

        //mapeo de categoria al dto
        for(Categoria cat : listaCategorias) {
            CategoriaSalidaDTO catDTO = new CategoriaSalidaDTO();
            catDTO.setId(cat.getId());
            catDTO.setNombre(cat.getNombre());
            catDTO.setDescripcion(cat.getDescripcion());

            categoriasDTO.add(catDTO);
        }
        return categoriasDTO;
    }

    public void actualizarCategoria(int id, CategoriaEntradaDTO dto) throws IllegalArgumentException {

        // Validación que el nombre no este vacio
        if(dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del categoria no puede ser nulo");
        }

        // Validacion si existe la categoria en la BD
        Categoria catEntidad = categoriaRepository.obtenerCategoriaID(id);
        if(catEntidad == null) {
            throw new IllegalArgumentException("El categoria no existe");
        }

        // mapeo del dto a entidad actualizar
        catEntidad.setNombre(dto.getNombre().trim());
        catEntidad.setDescripcion(dto.getDescripcion());

        categoriaRepository.actualizar(catEntidad);
    }

    public void eliminarCategoria(int id) {
        Categoria catEntidad = categoriaRepository.obtenerCategoriaID(id);

        // validar si existe la categoria
        if(catEntidad == null) {
            throw new IllegalArgumentException("La categoria no existe");
        }

        // validar si existen movimientos asociados a la categoria
        boolean tieneAsociados = movimientoRepository.existeMovimientoPorCategoria(catEntidad.getId());

        if (tieneAsociados) {
            throw new IllegalArgumentException("La categoria tiene asociados movimientos");
        }

        categoriaRepository.eliminar(catEntidad.getId());

    }

    public CategoriaSalidaDTO obtenerCategoriaID(int id) {

        Categoria catEntidad = categoriaRepository.obtenerCategoriaID(id);
        CategoriaSalidaDTO catDTO = new CategoriaSalidaDTO();

        // Mapeo al dto
        catDTO.setId(catEntidad.getId());
        catDTO.setNombre(catEntidad.getNombre());
        catDTO.setDescripcion(catEntidad.getDescripcion());

        return catDTO;
    }

}
