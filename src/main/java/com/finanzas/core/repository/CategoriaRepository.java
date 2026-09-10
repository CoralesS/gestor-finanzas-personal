package com.finanzas.core.repository;

import com.finanzas.core.config.ConexionBD;
import com.finanzas.core.domain.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository {

    // Constructor
    public CategoriaRepository() {
    }

    // Métodos
    public void guardar(Categoria categoria) {
        String sql = "INSERT INTO categoria(cat_nombre, cat_descripcion) " +
                "VALUES (?, ?)";

        // Try-with-resources
        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS)) {

            // Extracción de datos del objeto para la consulta
            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());

            // verificacion de la inserción
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Registro guardado exitosamente");
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        categoria.setId(generatedKeys.getInt(1));
                    }
                }

            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el categoria: " + e.getMessage());
        }

    }

    public void actualizar(Categoria categoria) {

        String sql = "UPDATE categoria SET cat_nombre = ?, cat_descripcion = ? WHERE id_categoria = ?";

        try (Connection conexion = ConexionBD.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());
            pstmt.setInt(3, categoria.getId());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Registro actualizado exitosamente");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el categoria: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";

        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)){

            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
            System.out.println("Registro eliminado exitosamente");
            }
        } catch(SQLException e) {
            System.out.println("Error al eliminar el categoria: " + e.getMessage());
        }
    }

    public Categoria obtenerCategoriaID(int idCategoria) {
        String sql = "SELECT * FROM categoria WHERE id_categoria = ?";
        Categoria categoria = null;

        try (Connection conexion = ConexionBD.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, idCategoria);
            ResultSet rs = pstmt.executeQuery();
            // Avanza una posición al puntero, true si encuentra datos
            if (rs.next()) {
                //Extracción de la BD
                int id = rs.getInt("id_categoria");
                String nombre = rs.getString("cat_nombre");
                String descripcion = rs.getString("cat_descripcion");

                // Instancia del objeto categoria
                categoria = new Categoria(id, nombre, descripcion);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el categoria: " + e.getMessage());
        }
        return categoria;
    }

    public List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT * FROM categoria";

        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {


            // Avanza una posición al puntero, true si encuentra datos
            while (rs.next()) {
                //Extracción de la BD
                int id = rs.getInt("id_categoria");
                String nombre = rs.getString("cat_nombre");
                String descripcion = rs.getString("cat_descripcion");

                // Instancia del objeto categoria
                Categoria categoria = new Categoria(id, nombre, descripcion);
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener las categorias: " + e.getMessage());
        }
        return categorias;
    }

}
