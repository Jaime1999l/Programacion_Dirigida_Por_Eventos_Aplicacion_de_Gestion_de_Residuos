package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.R;

public class MapActivity extends AppCompatActivity {

    private CustomMapView customMapView;
    private SeekBar zoomSlider;
    private int zoomLevel = 1; // Nivel de zoom inicial

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_mapa);

        // Agregar el CustomMapView al layout existente
        customMapView = new CustomMapView();
        FrameLayout frameLayout = findViewById(R.id.map_frame);
        frameLayout.addView(customMapView);

        // Configurar el botón para cambiar de vista
        Button btnChangeView = findViewById(R.id.btn_change_view);
        btnChangeView.setOnClickListener(view -> changeView());

        // Configurar el botón para centrar el mapa en un punto de interés
        Button btnCenter = findViewById(R.id.btn_center);
        btnCenter.setOnClickListener(view -> centerOnPoint(300, 300));

        // Configurar el slider de zoom
        zoomSlider = findViewById(R.id.zoom_slider);
        zoomSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                zoomLevel = progress;
                customMapView.invalidate(); // Redibujar el mapa con el nuevo nivel de zoom
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No se necesita implementar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MapActivity.this, "Zoom: " + zoomLevel, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Cambia la vista del mapa (puedes definir diferentes modos de visualización)
    private void changeView() {
        customMapView.toggleViewMode();
        customMapView.invalidate(); // Redibuja el mapa
    }

    // Centra el mapa en un punto de interés específico
    private void centerOnPoint(float x, float y) {
        customMapView.setCenterPoint(x, y);
        customMapView.invalidate(); // Redibuja el mapa centrado en el punto
    }

    // Clase interna para representar el mapa personalizado con detalles
    private class CustomMapView extends View {

        private Paint paint;
        private float centerX = 0;
        private float centerY = 0;
        private boolean isSatelliteView = false;

        public CustomMapView() {
            super(MapActivity.this);
            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Ajustar el zoom y la posición del mapa
            canvas.scale(zoomLevel, zoomLevel, centerX, centerY);

            // Dibujar terreno base según el modo de vista
            if (isSatelliteView) {
                drawSatelliteView(canvas);
            } else {
                drawTerrain(canvas);
            }

            // Dibujar el contenido del mapa
            drawContent(canvas);
        }

        // Método para alternar el modo de vista
        public void toggleViewMode() {
            isSatelliteView = !isSatelliteView;
        }

        // Establece el centro del mapa
        public void setCenterPoint(float x, float y) {
            centerX = x;
            centerY = y;
        }

        // Método para dibujar la vista satelital
        private void drawSatelliteView(Canvas canvas) {
            // Simula la vista satelital con un fondo diferente
            canvas.drawColor(Color.DKGRAY);
            // Puedes agregar más detalles de la vista satelital aquí
        }

        // Método para dibujar el terreno base
        private void drawTerrain(Canvas canvas) {
            canvas.drawColor(Color.parseColor("#F0E68C")); // Fondo del mapa
            paint.setColor(Color.DKGRAY);
            paint.setStrokeWidth(2);
            for (int i = 0; i < getWidth(); i += 100) {
                canvas.drawLine(i, 0, i, getHeight(), paint);
            }
            for (int i = 0; i < getHeight(); i += 100) {
                canvas.drawLine(0, i, getWidth(), i, paint);
            }
        }

        // Método para dibujar el contenido del mapa (calles, ríos, etc.)
        private void drawContent(Canvas canvas) {
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);
            addRecyclePoint(canvas, 200, 300, "Punto 1");
            addRecyclePoint(canvas, 400, 500, "Punto 2");
            addRecyclePoint(canvas, 600, 200, "Punto 3");
        }

        // Método para agregar un punto de reciclaje en el mapa
        private void addRecyclePoint(Canvas canvas, float x, float y, String label) {
            canvas.drawCircle(x, y, 20, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(30);
            canvas.drawText(label, x + 30, y, paint);
            paint.setColor(Color.GREEN);
        }
    }
}

