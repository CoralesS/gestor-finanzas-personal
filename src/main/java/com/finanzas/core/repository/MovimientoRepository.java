package com.finanzas.core.repository;

import com.finanzas.core.config.ConexionBD;
import com.finanzas.core.domain.Movimiento;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovimientoRepository {

    // Constructor
    public MovimientoRepository() {
    }

    // Métodos
    public void guardar(Movimiento movimiento) {
        String sql = "INSERT INTO movimiento (mov_concepto, mov_fecha, mov_monto, id_categoria) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1,movimiento.getConcepto());
            pstmt.setDate(2, java.sql.Date.valueOf(movimiento.getFecha()));
            pstmt.setDouble(3, movimiento.getMonto());
            pstmt.setInt(4, movimiento.getIdCategoria());

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Registro guardado exitosamente");
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        movimiento.setId(rs.getInt(1));
                    }
                }
            }

        }catch (SQLException e){
            System.out.println("Error al insertar el registro" + e.getMessage());
        }


    }
    public void eliminar(int id) {
        String sql = "DELETE FROM movimiento WHERE id_categoria = ?";

        try (Connection conexion = ConexionBD.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql)){

            pstmt.setInt(1, id);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Registro eliminado exitosamente");
            }

        }catch (SQLException e){
            System.out.println("Error al eliminar el registro" + e.getMessage());
        }

    }

    public void actualizar(Movimiento movimiento) {
        String sql = "UPDATE movimiento SET mov_fecha = ?, mov_monto = ?, id_categoria = ? WHERE id_movimiento = ?";

        try(Connection conexion = ConexionBD.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setDate(1, java.sql.Date.valueOf(movimiento.getFecha()));
            pstmt.setDouble(2, movimiento.getMonto());
            pstmt.setInt(3, movimiento.getIdCategoria());
            pstmt.setInt(4, movimiento.getId());

        }catch (SQLException e){
            System.out.println("Error al actualizar el registro" + e.getMessage());
        }
    }

    public Movimiento obtener(int id) {
        String sql = "SELECT * FROM movimiento WHERE id_movimiento = ?";
        Movimiento movimiento = null;
        try(Connection conexion = ConexionBD.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql)){

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // Extracción de datos
            int idMov = rs.getInt("id_movimiento");
            String concepto =  rs.getString("mov_concepto");
            double monto = rs.getDouble("mov_monto");
            LocalDate fecha = rs.getDate("mov_fecha").toLocalDate();
            int idCategoria = rs.getInt("id_categoria");

            // crear el objeto
            movimiento = new Movimiento(idMov,concepto, monto, fecha, idCategoria);
        }catch (SQLException e){
            System.out.println("Error al obtener el registro" + e.getMessage());
        }
        return movimiento;
    }

    public List<Movimiento> obtenerTodos() {
        List<Movimiento> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM movimiento";

        try(Connection conexion = ConexionBD.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();){

            while(rs.next()){
                // Extracción de datos
                int idMov = rs.getInt("id_movimiento");
                String concepto =  rs.getString("mov_concepto");
                double monto = rs.getDouble("mov_monto");
                LocalDate fecha = rs.getDate("mov_fecha").toLocalDate();
                int idCategoria = rs.getInt("id_categoria");

                // crear el objeto
                Movimiento movimiento = new Movimiento(idMov,concepto, monto, fecha, idCategoria);
                movimientos.add(movimiento);
            }

        }catch (SQLException e){
            System.out.println("Error al obtener el registro" + e.getMessage());
        }
        return movimientos;
    }

    public boolean existeMovimientoPorCategoria(int idCategoria) {
        String sql = "SELECT 1 FROM movimiento WHERE id_categoria = ? LIMIT 1";

        boolean existeFila = false;

        try(Connection conexion = ConexionBD.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                existeFila = true;
            }

        }catch (SQLException e){
            System.out.println("Error al obtener el registro" + e.getMessage());
        }
        return existeFila;
    }

    public List<Movimiento> obtenerPorCategoria(int idCategoria) {
        String sql = "SELECT * FROM movimiento WHERE id_categoria = ?";
        List<Movimiento> movimientos = new ArrayList<>();

        try(Connection conexion = ConexionBD.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, idCategoria);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int idMov = rs.getInt("id_movimiento");
                String concepto =  rs.getString("mov_concepto");
                double monto = rs.getDouble("mov_monto");
                LocalDate fecha = rs.getDate("mov_fecha").toLocalDate();
                int idCat =  rs.getInt("id_categoria");

                Movimiento movimiento = new Movimiento(idMov,concepto, monto, fecha, idCat);
                movimientos.add(movimiento);

            }
        }catch (SQLException e){
            System.out.println("Error al obtener el registro" + e.getMessage());
        }
        return movimientos;
    }

}

