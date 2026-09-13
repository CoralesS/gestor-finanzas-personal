package com.finanzas.core.service;

import com.finanzas.core.domain.Movimiento;
import com.finanzas.core.dto.MovimientoEntradaDTO;
import com.finanzas.core.dto.MovimientoSalidaDTO;
import com.finanzas.core.repository.MovimientoRepository;

import java.util.ArrayList;
import java.util.List;

public class MovimientoService {

    private MovimientoRepository movRepository;

    // Constructor
    public MovimientoService(MovimientoRepository movRepository) {
        this.movRepository = movRepository;
    }

    // Métodos
    public void registarMovimiento(MovimientoEntradaDTO dto) throws IllegalArgumentException {
        if (dto.getConcepto() == null) {
            throw new IllegalArgumentException();
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setConcepto(dto.getConcepto());
        movimiento.setFecha(dto.getFecha());
        movimiento.setMonto(dto.getMonto());
        movimiento.setIdCategoria(dto.getIdcategoria());

        movRepository.guardar(movimiento);
    }

    public List<MovimientoSalidaDTO> obtenerMovimientosPorCategoria(int id_categoria) throws IllegalArgumentException {

        List<Movimiento> listaMovimientos = movRepository.obtenerPorCategoria(id_categoria);
        List<MovimientoSalidaDTO> listaSalidas = new ArrayList<>();

        for (Movimiento movimiento : listaMovimientos) {
            MovimientoSalidaDTO dto = new MovimientoSalidaDTO();

            dto.setId(movimiento.getId());
            dto.setConcepto(movimiento.getConcepto());
            dto.setFecha(movimiento.getFecha());
            dto.setMonto(movimiento.getMonto());

            listaSalidas.add(dto);

        }

        return listaSalidas;
    }


    public void eliminarMovimiento(int id) {
        movRepository.eliminar(id);
    }

    public void actualizarMovimiento(MovimientoEntradaDTO dto) throws IllegalArgumentException {

        if (dto.getConcepto() == null) {
            throw new IllegalArgumentException();
        }

        Movimiento movimiento = new Movimiento();

        movimiento.setConcepto(dto.getConcepto());
        movimiento.setMonto(dto.getMonto());
        movimiento.setFecha(dto.getFecha());
        movimiento.setId(dto.getId());
        movRepository.actualizar(movimiento);
    }

    public MovimientoSalidaDTO obtenerMovimientoPorID(int id) throws IllegalArgumentException {

        Movimiento movimiento = movRepository.obtenerMovimientoPorID(id);

        if (movimiento == null) {
            throw new IllegalArgumentException();
        }

        // mapeo de salida
        MovimientoSalidaDTO dto = new MovimientoSalidaDTO();
        dto.setId(movimiento.getId());
        dto.setConcepto(movimiento.getConcepto());
        dto.setFecha(movimiento.getFecha());
        dto.setMonto(movimiento.getMonto());

        return dto;
    }
}
