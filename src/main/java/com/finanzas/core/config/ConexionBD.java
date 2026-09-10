package com.finanzas.core.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // credenciales
    private static final String URL = "jdbc:mysql://localhost:3306/finanza_personal";
    private static final String USER = "root";
    private static final String PASSWORD = "Coral23";

    private static Connection conexion = null;

    public static Connection getConnection() {
        try {
            //establecer la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion establecida!");
        } catch (SQLException e){
            System.out.println("Error al conectar con la base de datos");
        }
        return conexion;
    }

}
