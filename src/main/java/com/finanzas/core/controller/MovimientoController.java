package com.finanzas.core.controller;

import com.finanzas.core.dto.CategoriaSalidaDTO;
import com.finanzas.core.dto.MovimientoEntradaDTO;
import com.finanzas.core.dto.MovimientoSalidaDTO;
import com.finanzas.core.repository.MovimientoRepository;
import com.finanzas.core.service.CategoriaService;
import com.finanzas.core.service.MovimientoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/MovimientoController")
public class MovimientoController extends HttpServlet {

    private MovimientoService  movimientoService;
    //private final CategoriaService categoriaService;

    //Métodos Servelet
    @Override
    public void init() throws ServletException {
        MovimientoRepository movRepo = new MovimientoRepository();
        this.movimientoService = new MovimientoService(movRepo);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        // Bloqueo si no hay acción o intentan entrar sin el ID de la categoría
        if (accion == null || request.getParameter("idcategoria") == null) {
            response.sendRedirect("CategoriaController?accion=listar");
            return;
        }

        switch (accion) {
            case "listar":
                listarMovimientos(request, response);
                break;
            case "eliminar":
                eliminarMovimiento(request, response);
                break;
            case "editar":
                editarMovimiento(request, response);
                break;
            default:
                response.sendRedirect("CategoriaController?accion=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        switch (accion) {
            case "crear":
                crearMovimiento(request, response);
                break;

            case "actualizar":
                actualizarMovimiento(request, response);
                break;

            default:
                response.sendRedirect("CategoriaController?accion=listar");
        }
    }


    // Métodos
    public void crearMovimiento(HttpServletRequest request, HttpServletResponse response){
        try {
            //nombre de la categoria
            String nombreCat = request.getParameter("nombre");
            // extraer los datos del formulario web
            String conceptoWeb = request.getParameter("concepto");
            String montoWeb = request.getParameter("monto");
            String fechaWeb = request.getParameter("fecha");
            String idCategoriaWeb = request.getParameter("idcategoria");

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

            response.sendRedirect("MovimientoController?accion=listar&idcategoria=" + idCategoria + "&nombre="+ nombreCat + "&mensaje=exito");


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

    public void eliminarMovimiento(HttpServletRequest request, HttpServletResponse response) {
        try {
            String nombreCat = request.getParameter("nombre");
            String idCategoriaWeb = request.getParameter("idcategoria");
            int id = Integer.parseInt(request.getParameter("id"));
            movimientoService.eliminarMovimiento(id);
            response.sendRedirect("/MovimientoController?accion=listar&idcategoria=" + idCategoriaWeb + "&nombre=" + nombreCat + "&mensaje=eliminado");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID inválido.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void listarMovimientos (HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // extraccion de ID de la categoria
            String idCategoriaWeb = request.getParameter("idcategoria");
            int idCategoria = Integer.parseInt(idCategoriaWeb);

            // peticion al servicio los movimientos de la categoria
            List<MovimientoSalidaDTO> listaMovimientos = movimientoService.obtenerMovimientosPorCategoria(idCategoria);

            // para mostrar la categoria actual
            request.setAttribute("movimientos", listaMovimientos);
            request.getRequestDispatcher("/listaMovimientos.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.err.println("Error de seguridad: ID de categoría inválido o ausente");
            // ruta para la pagina principal de categorías para evitar que el sistema se caiga
            response.sendRedirect("CategoriaController?accion=listar&error=id_invalido");
        } catch (IllegalArgumentException | ServletException e) {
            // Si mandan un ID de una categoría que ya fue eliminada o no existe
            response.sendRedirect("CategoriaController?accion=listar&error=categoria_no_existe");
        }
    }

    private void editarMovimiento(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        try {
            int idMovimiento = Integer.parseInt(request.getParameter("id"));

            MovimientoSalidaDTO catSalidaDTO = movimientoService.obtenerMovimientoPorID(idMovimiento);
            request.setAttribute("movimiento", catSalidaDTO);

            request.getRequestDispatcher("/editarMovimiento.jsp").forward(request, response);
        } catch (IOException e) {
             throw new RuntimeException(e);
        }
    }

    public void actualizarMovimiento (HttpServletRequest request, HttpServletResponse response) throws IOException {

        String idCategoria = request.getParameter("idcategoria");
        String nombreCat = request.getParameter("nombre");

        try {
            // atrapar como cadenas de texto de la web
            String conceptoWeb = request.getParameter("concepto");
            String montoWeb = request.getParameter("monto");
            String fechaWeb = request.getParameter("fecha");
            String idMovimientoWeb = request.getParameter("id");

            // conversion de datos para el dto
            Double monto = Double.parseDouble(montoWeb);
            LocalDate fecha = LocalDate.parse(fechaWeb);
            int idMovimiento = Integer.parseInt(idMovimientoWeb);

            MovimientoEntradaDTO dto = new MovimientoEntradaDTO();
            dto.setConcepto(conceptoWeb);
            dto.setMonto(monto);
            dto.setFecha(fecha);
            dto.setId(idMovimiento);

            // delegar al servicio
            movimientoService.actualizarMovimiento(dto);
            String urlExito = "MovimientoController?accion=listar&idcategoria=" + idCategoria + "&nombre=" + nombreCat + "&mensaje=exito";
            response.sendRedirect(urlExito);
            //System.out.println("Actualizando el ID: " + dto.getId());

        } catch (NumberFormatException e) {
            // Si no se pudo convertir el monto a Double o el ID a Integer
            System.out.println("Error de formato numérico: " + e.getMessage());
            response.sendRedirect("MovimientoController?accion=listar&idcategoria=" + idCategoria + "&nombre=" + nombreCat + "&error=Error en los datos numéricos");

        } catch (DateTimeParseException e) {
            // Si no se pudo convertir el texto a LocalDate
            System.out.println("Error de formato de fecha: " + e.getMessage());
            response.sendRedirect("MovimientoController?accion=listar&idcategoria=" + idCategoria + "&nombre=" + nombreCat + "&error=La fecha ingresada no es válida");

        } catch (Exception e) {
            // Para cualquier otro error
            System.out.println("Error general al actualizar: " + e.getMessage());
            response.sendRedirect("MovimientoController?accion=listar&idcategoria=" + idCategoria + "&nombre=" + nombreCat + "&error=Ocurrió un error inesperado al actualizar");
        }

    }

}
