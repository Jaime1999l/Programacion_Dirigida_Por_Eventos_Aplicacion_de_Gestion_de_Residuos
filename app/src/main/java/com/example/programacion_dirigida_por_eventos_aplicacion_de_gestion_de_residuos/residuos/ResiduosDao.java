package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos.Residuos;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos.estadisticas.Estadistica;

import java.util.List;

@Dao
public interface ResiduosDao {

    @Insert
    void insertarResiduos(Residuos residuos);

    // Obtener estadísticas por contenedor en general (no se usa tramo o fecha)
    @Query("SELECT tipoContenedor, SUM(bolsas) as totalBolsas FROM registro_residuos GROUP BY tipoContenedor")
    List<Estadistica> obtenerEstadisticasPorContenedor();

    // Obtener estadísticas por día en general (no se usa tramo o fecha)
    @Query("SELECT fecha, SUM(bolsas) as totalBolsas FROM registro_residuos GROUP BY fecha")
    List<Estadistica> obtenerEstadisticasPorDia();

    // Obtener estadísticas de bolsas por contenedor en un día específico (para gráfico circular)
    @Query("SELECT tipoContenedor, SUM(bolsas) as totalBolsas FROM registro_residuos WHERE fecha = :fecha GROUP BY tipoContenedor")
    List<Estadistica> obtenerEstadisticasPorContenedorEnFecha(String fecha);

    // Obtener estadísticas de bolsas por día en un día específico
    @Query("SELECT fecha, SUM(bolsas) as totalBolsas FROM registro_residuos WHERE fecha = :fecha GROUP BY fecha")
    List<Estadistica> obtenerEstadisticasPorDiaEnFecha(String fecha);

    // Obtener estadísticas por contenedor en un tramo de fechas (para gráfico de barras)
    @Query("SELECT tipoContenedor, SUM(bolsas) as totalBolsas FROM registro_residuos WHERE fecha BETWEEN :fechaInicio AND :fechaFin GROUP BY tipoContenedor")
    List<Estadistica> obtenerEstadisticasPorContenedorEnTramo(String fechaInicio, String fechaFin);



    // Obtener estadísticas por día en un tramo de fechas (para gráfico de barras)
    @Query("SELECT fecha, SUM(bolsas) as totalBolsas FROM registro_residuos WHERE fecha BETWEEN :fechaInicio AND :fechaFin GROUP BY fecha")
    List<Estadistica> obtenerEstadisticasPorDiaEnTramo(String fechaInicio, String fechaFin);

    @Query("SELECT * FROM registro_residuos WHERE fecha = :fecha")
    List<Residuos> obtenerResiduosPorFecha(String fecha);



}
