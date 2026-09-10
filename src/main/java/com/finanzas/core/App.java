package com.finanzas.core;

import com.finanzas.core.config.ConexionBD;
import com.finanzas.core.domain.Categoria;
import com.finanzas.core.repository.CategoriaRepository;

public class App {
    public static void main(String[] args) {
        ConexionBD.getConnection();

        Categoria categoria = new Categoria("II", "II realizados en ciudad");
        Categoria categoriaM;
        CategoriaRepository cr = new CategoriaRepository();
        cr.guardar(categoria);
        // categoriaRepository.actualizar(categoria);
        //categoriaM = cr.obtenerCategoria(1);

        //categoriaM.setNombre("Manuelito");
        //categoriaM.setDescripcion("Ottito");
        cr.eliminar(4);

        /* System.out.println(categoria.getId());
        System.out.println("\n");
        System.out.println(categoria.getNombre());
        System.out.println("\n");
        System.out.println(categoria.getDescripcion());

         */

    }
}
