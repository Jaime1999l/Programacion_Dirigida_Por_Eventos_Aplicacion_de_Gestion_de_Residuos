package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.recordatorios;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public long date; // Para almacenar la fecha
    public String description; // Para almacenar la descripción del recordatorio

    // Constructor
    public Reminder(long date, String description) {
        this.date = date;
        this.description = description;
    }

    // Getters
    public long getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}

