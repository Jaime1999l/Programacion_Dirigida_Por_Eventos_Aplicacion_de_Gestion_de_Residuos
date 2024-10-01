package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos_main.residuos.residuos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "registro_residuos")
public class Residuos {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fecha;
    private int bolsas;
    private String tipoContenedor;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getBolsas() {
        return bolsas;
    }

    public void setBolsas(int bolsas) {
        this.bolsas = bolsas;
    }

    public String getTipoContenedor() {
        return tipoContenedor;
    }

    public void setTipoContenedor(String tipoContenedor) {
        this.tipoContenedor = tipoContenedor;
    }
}
