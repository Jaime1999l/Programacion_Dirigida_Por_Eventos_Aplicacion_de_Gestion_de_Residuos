package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos.Residuos;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos.ResiduosDao;

@androidx.room.Database(entities = {Residuos.class}, version = 1)
public abstract class DatabaseResiduos extends RoomDatabase {

    public abstract ResiduosDao residuosDao();

    private static volatile DatabaseResiduos INSTANCE;

    public static DatabaseResiduos getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseResiduos.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseResiduos.class, "residuos_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
