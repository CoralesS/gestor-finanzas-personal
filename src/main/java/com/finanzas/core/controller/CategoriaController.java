package com.finanzas.core.controller;

import com.finanzas.core.dto.CategoriaEntradaDTO;
import com.finanzas.core.dto.CategoriaSalidaDTO;
import com.finanzas.core.service.CategoriaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class CategoriaController {

    private final CategoriaService categoriaService;

    // Constructor
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Métodos
    public void crearCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // extraccion de datos de la solicitud
            String nombreWeb = request.getParameter("nombre");
            String descripcionWeb = request.getParameter("descripcion");

            // Empaquetado al dto
            CategoriaEntradaDTO categoriaEntradaDTO = new CategoriaEntradaDTO();
            categoriaEntradaDTO.setNombre(nombreWeb);
            categoriaEntradaDTO.setDescripcion(descripcionWeb);

            // Pasar responsabilidad al Servicio
            categoriaService.crearCategoria(categoriaEntradaDTO);

            response.sendRedirect("/listaCategorias.jsp?mensaje=exito");
        } catch (IOException e) {
            request.setAttribute("errorPantalla", e.getMessage());
            request.getRequestDispatcher("/formularioCategoria.jsp").forward(request, response);
            throw new RuntimeException(e);
        }


    }

    public void listarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // lista de dtos del servicio
            List<CategoriaSalidaDTO> lista = categoriaService.obtenerCategorias();

            // guardado de la lista en la memoria de la petición web
            request.setAttribute("categorias", lista);

            // Envio del usuario a la página donde se mostrará la tabla
            request.getRequestDispatcher("/formularioCategoria.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Error del sistema al listar categorias: " + e.getMessage());
        }
    }

    public void actualizarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Extraer y convertir el ID
            int id = Integer.parseInt(request.getParameter("id"));

            // empaquetado del dto
            CategoriaEntradaDTO categoriaEntradaDTO = new CategoriaEntradaDTO();
            categoriaEntradaDTO.setNombre(request.getParameter("nombre"));
            categoriaEntradaDTO.setDescripcion(request.getParameter("descripcion"));

            // delegar al servicio
            categoriaService.actualizarCategoria(id, categoriaEntradaDTO);

            // redirección a la tabla actualizada
            response.sendRedirect("/ControladorServlet?accion=listar&mensaje=actualizado");
        }catch (NumberFormatException e) {
            request.setAttribute("error", "Error de seguridad: EL ID enviado no es un número válido");
        }catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
        }
    }

    public void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // extracción de ID de la categoria
            int id = Integer.parseInt(request.getParameter("id"));

            // delegar al servicio
            categoriaService.eliminarCategoria(id);
            response.sendRedirect("ControladorServlet?accion=listar&mensaje=eliminado");
        }catch (NumberFormatException e) {
            request.setAttribute("error", "ID inválido");
        } catch (IllegalStateException e){
            // Atrapa el error si la categoria ya tiene movimientos
            request.setAttribute("error", e.getMessage());
        } catch (IllegalArgumentException e) {
            // atrapa si la categoria ya no existe
            request.setAttribute("error", e.getMessage());
        }
    }


}
