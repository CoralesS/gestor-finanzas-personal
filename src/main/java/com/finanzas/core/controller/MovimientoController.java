package com.finanzas.core.controller;

import com.finanzas.core.dto.MovimientoEntradaDTO;
import com.finanzas.core.dto.MovimientoSalidaDTO;
//import com.finanzas.core.service.CategoriaService;
import com.finanzas.core.service.MovimientoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MovimientoController {

    private final MovimientoService  movimientoService;
    //private final CategoriaService categoriaService;

    // constructor
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
        //this.categoriaService = categoriaService;
    }

    // Métodos
    public void crearMovimiento(HttpServletRequest request, HttpServletResponse response){
        try {
            // extraer los datos del formulario web
            String conceptoWeb = request.getParameter("concepto");
            String montoWeb = request.getParameter("monto");
            String fechaWeb = request.getParameter("fecha");
            String idCategoriaWeb = request.getParameter("idCategoria");

            // conversión de datos (parseo)
            double monto = Double.parseDouble(montoWeb);
            LocalDate fecha = LocalDate.parse(fechaWeb);
            int idCategoria = Integer.parseInt(idCategoriaWeb);

            // empaquetado del dto
            MovimientoEntradaDTO dto = new MovimientoEntradaDTO();
            dto.setConcepto(conceptoWeb);
            dto.setMonto(monto);
            dto.setFecha(fecha);
            dto.setIdcategoria(idCategoria);

            // delegar al servicio
            movimientoService.registarMovimiento(dto);

            response.sendRedirect("MovimientoServlet?accion=listar&mensaje=creado");
        } catch (NumberFormatException e) {
            // frena el proceso si hay letran en el monto o en el id categoria
            request.setAttribute("error", "EL monto o categoría no tienen formato numerico");
        } catch (DateTimeParseException e) {
            // si se envia una fecha invalida
            request.setAttribute("error", "Fecha invalida");
        } catch (IllegalArgumentException | IOException e) {
            request.setAttribute("error", e.getMessage());
        }
    }

    /*
    public void eliminarMovimiento(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            movimientoService.eliminarMovimiento(id);
            response.sendRedirect("MovimientoServlet?accion=listar&mensaje=eliminado");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID inválido.");
        }

     */

    public void listarMovimientos (HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // extraccion de ID de la categoria
            String idCategoriaWeb = request.getParameter("idcategoria");
            int idCategoria = Integer.parseInt(idCategoriaWeb);

            // peticion al servicio los movimientos de la categoria
            List<MovimientoSalidaDTO> listaMovimientos = movimientoService.obtenerMovimientosPorCategoria(idCategoria);

            // para mostrar la categoria actual
            request.setAttribute("listaMovimientos", listaMovimientos);
            request.getRequestDispatcher("/vistas/listaMovimientos.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.err.println("Error de seguridad: ID de categoría inválido o ausente");
            // ruta para la pagina principal de categorías para evitar que el sistema se caiga
            response.sendRedirect("CategoriaServlet?accion=listar&error=id_invalido");
        } catch (IllegalArgumentException | ServletException e) {
            // Si mandan un ID de una categoría que ya fue eliminada o no existe
            response.sendRedirect("CategoriaServlet?accion=listar&error=categoria_no_existe");
        }
    }

}
