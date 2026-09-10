package com.finanzas.core.dto;

import java.time.LocalDate;

public class MovimientoEntradaDTO {

    // Atributos
    private String concepto;
    private LocalDate fecha;
    private double monto;
    private int idcategoria;

    // Getters y setters
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }
}
