package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.recordatorios.Reminder;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.recordatorios.ReminderDao;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos.Residuos;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos.ResiduosDao;

@Database(entities = {Residuos.class, Reminder.class}, version = 2)
public abstract class DatabaseResiduos extends RoomDatabase {

    public abstract ResiduosDao residuosDao();
    public abstract ReminderDao reminderDao();

    private static volatile DatabaseResiduos INSTANCE;

    public static DatabaseResiduos getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseResiduos.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseResiduos.class, "residuos_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
