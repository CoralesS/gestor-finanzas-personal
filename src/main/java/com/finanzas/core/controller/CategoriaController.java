package com.finanzas.core.controller;

import com.finanzas.core.dto.CategoriaEntradaDTO;
import com.finanzas.core.dto.CategoriaSalidaDTO;
import com.finanzas.core.repository.CategoriaRepository;
import com.finanzas.core.repository.MovimientoRepository;
import com.finanzas.core.service.CategoriaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
//Etiqueta que define la URL del Controlador
@WebServlet("/CategoriaController")
public class CategoriaController extends HttpServlet {

    private CategoriaService categoriaService;

    //Metodos Servelet
    @Override
    public void init() throws ServletException {
        CategoriaRepository catRepo = new CategoriaRepository();
        MovimientoRepository movRepo = new MovimientoRepository();
        this.categoriaService = new CategoriaService(catRepo, movRepo);
    }

    // 4. Enrutador para Clics y URLs
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                listarCategorias(request, response);
                break;
            case "eliminar":
                eliminarCategoria(request, response);
                break;
            case "editar":
                mostrarFormularioEditar(request, response);
                break;
            case "seleccionar":
                mostrarMatrizCategorias(request, response);
                break;
            default:
                listarCategorias(request, response);
        }
    }

    // Enrutador para Formularios
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        switch (accion) {
            case "crear":
                crearCategoria(request, response);
                break;
            case "actualizar":
                actualizarCategoria(request, response);
                break;
            default:
                response.sendRedirect("CategoriaController?accion=listar");
        }
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

            // Redirigir al controlador con la acción listar, no al JSP directo
            response.sendRedirect("CategoriaController?accion=listar&mensaje=exito");
            //response.sendRedirect("/formularioCategoria.jsp?mensaje=exito");
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
            response.sendRedirect("/CategoriaController?accion=listar&mensaje=actualizado");
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
            response.sendRedirect("/CategoriaController?accion=listar&mensaje=eliminado");
        } catch (IllegalArgumentException e) {
            System.out.println("Bloqueo del Servicio: " + e.getMessage());
            // alerta roja en la vista
            response.sendRedirect("CategoriaController?accion=listar&error=categoria_en_uso");

        } catch (Exception e) {
            // Para cualquier otro error inesperado
            response.sendRedirect("CategoriaController?accion=listar&error=fallo_eliminar");
        }
    }

    public void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            CategoriaSalidaDTO catSalidaDTO = categoriaService.obtenerCategoriaID(id);
            request.setAttribute("categoria", catSalidaDTO);

            request.getRequestDispatcher("/editarCategoria.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("CategoriaController?accion=listar&error=fallo_edicion");
        }
    }

    public void mostrarMatrizCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<CategoriaSalidaDTO> lista = categoriaService.obtenerCategorias();

            request.setAttribute("categorias", lista);

            // forward hacia la nueva vista
            request.getRequestDispatcher("/seleccionarCategoria.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Error al cargar la matriz: " + e.getMessage());
            response.sendRedirect("index.jsp?error=fallo_cargar");
        }
    }
}
