package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.recordatorios;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert
    void insert(Reminder reminder);

    @Query("SELECT * FROM reminders WHERE date = :selectedDate")
    List<Reminder> getRemindersByDate(long selectedDate);
}


