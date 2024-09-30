package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.R;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.view.GraficoBarras;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.view.GraficoCircular;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.database.DatabaseResiduos;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos.Residuos;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos.estadisticas.Estadistica;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private DatabaseResiduos db;
    EditText editBolsas, editFecha;
    EditText editFechaCircular, editFechaInicio, editFechaFin;
    Spinner spinnerContenedor;
    Button btnAgregar, btnMostrarGraficos;
    GraficoBarras chartDia;
    GraficoCircular chartContenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Inicializamos la base de datos
        db = DatabaseResiduos.getDatabase(this);

        // Referenciamos los elementos de la interfaz
        editBolsas = findViewById(R.id.editBolsas);
        editFecha = findViewById(R.id.editFecha);
        editFechaCircular = findViewById(R.id.editFechaCircular);
        editFechaInicio = findViewById(R.id.editFechaInicio);
        editFechaFin = findViewById(R.id.editFechaFin);
        spinnerContenedor = findViewById(R.id.spinnerContenedor);
        btnAgregar = findViewById(R.id.btnAgregar);
        chartDia = findViewById(R.id.chartDia);  // Gráfico de barras
        chartContenedor = findViewById(R.id.chartContenedor);  // Gráfico circular
        btnMostrarGraficos = findViewById(R.id.btnMostrarGraficos);

        // Crear un adaptador para el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_contenedor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContenedor.setAdapter(adapter);

        // Botón para agregar datos
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarDatos();
            }
        });

        // Botón para mostrar los gráficos según el tramo temporal y el día específico
        btnMostrarGraficos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fechaCircular = editFechaCircular.getText().toString();
                String fechaInicio = editFechaInicio.getText().toString();
                String fechaFin = editFechaFin.getText().toString();
                mostrarEstadisticas(fechaCircular, fechaInicio, fechaFin);
            }
        });
    }

    private void agregarDatos() {
        final String fecha = editFecha.getText().toString();
        final int bolsas = Integer.parseInt(editBolsas.getText().toString());
        final String tipoContenedor = spinnerContenedor.getSelectedItem().toString();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Residuos residuos = new Residuos();
                residuos.setFecha(fecha);
                residuos.setBolsas(bolsas);
                residuos.setTipoContenedor(tipoContenedor);
                db.residuosDao().insertarResiduos(residuos);
            }
        });
    }

    private void mostrarEstadisticas(final String fechaCircular, final String fechaInicio, final String fechaFin) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Obtener estadísticas por contenedor para el gráfico circular (un día específico)
                List<Estadistica> estadisticasPorContenedor = db.residuosDao().obtenerEstadisticasPorContenedorEnFecha(fechaCircular);

                // Mostrar gráfico circular (distribución por contenedor)
                ArrayList<Float> pieData = new ArrayList<>();
                ArrayList<Integer> pieColors = new ArrayList<>();
                for (Estadistica estadistica : estadisticasPorContenedor) {
                    pieData.add((float) estadistica.totalBolsas);
                    pieColors.add(getColorByContenedor(estadistica.tipoContenedor));
                }

                // Obtener estadísticas por día para el gráfico de barras (tramo temporal)
                List<Estadistica> estadisticasPorDia = db.residuosDao().obtenerEstadisticasPorDiaEnTramo(fechaInicio, fechaFin);
                ArrayList<Float> barData = new ArrayList<>();
                ArrayList<Integer> barColors = new ArrayList<>();
                int colorIndex = 0;
                for (Estadistica estadistica : estadisticasPorDia) {
                    barData.add((float) estadistica.totalBolsas);
                    barColors.add(getColorByIndex(colorIndex++));  // Asignar colores dinámicos a las barras
                }

                // Actualizar los gráficos en el hilo principal (UI)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chartContenedor.setData(pieData, pieColors);  // Actualizar el gráfico circular
                        chartDia.setData(barData, barColors);  // Actualizar el gráfico de barras con colores
                    }
                });
            }
        });
    }

    private int getColorByContenedor(String tipoContenedor) {
        switch (tipoContenedor) {
            case "Papel":
                return android.graphics.Color.BLUE;
            case "Plástico":
                return android.graphics.Color.YELLOW;
            case "Orgánico":
                return android.graphics.Color.GREEN;
            case "Vidrio":
                return android.graphics.Color.RED;
            default:
                return android.graphics.Color.GRAY;
        }
    }

    // Método para obtener colores dinámicos para las barras del gráfico de barras
    private int getColorByIndex(int index) {
        int[] colors = {android.graphics.Color.BLUE, android.graphics.Color.RED, android.graphics.Color.GREEN, android.graphics.Color.YELLOW};
        return colors[index % colors.length];
    }
}

