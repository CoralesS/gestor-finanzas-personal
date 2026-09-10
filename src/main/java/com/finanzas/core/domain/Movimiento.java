package com.finanzas.core.domain;

import java.time.LocalDate;
import java.util.Date;

public class Movimiento {

    // Atributos
    private int id;
    private String concepto;
    private double monto;
    private LocalDate fecha;
    private int idCategoria;

    // Constructor
    public Movimiento(String concepto, double monto, LocalDate fecha) {
        this.concepto = concepto;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Movimiento(int id, String concepto, double monto, LocalDate fecha, int idCategoria) {
        this.id = id;
        this.concepto = concepto;
        this.monto = monto;
        this.fecha = fecha;
        this.idCategoria = idCategoria;
    }

    public Movimiento() {
    }

    // getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int id_categoria) {
        this.idCategoria = id_categoria;
    }


}
