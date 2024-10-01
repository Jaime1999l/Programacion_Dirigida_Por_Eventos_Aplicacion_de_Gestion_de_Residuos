package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos_main.residuos.activity;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.R;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    private ImageView mapImage, cursorImage;
    private Matrix matrix = new Matrix();
    private final Matrix savedMatrix = new Matrix();
    private final PointF start = new PointF();
    private ScaleGestureDetector scaleDetector;
    private final float[] matrixValues = new float[9];
    private float scale = 1f;
    private Button centerButton, addRecycleButton;
    private float imageWidth, imageHeight;
    private int containerWidth, containerHeight;
    private List<ImageView> recyclingPoints = new ArrayList<>();
    private List<float[]> recyclingPointPositions = new ArrayList<>();
    private FrameLayout mapContainer;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_mapa);

        mapImage = findViewById(R.id.map_image);
        cursorImage = findViewById(R.id.cursor_image);
        centerButton = findViewById(R.id.center_button);
        addRecycleButton = findViewById(R.id.add_recycle_button);
        mapContainer = findViewById(R.id.map_container);

        // Obtener el tamaño del contenedor del mapa
        mapImage.post(() -> {
            containerWidth = mapContainer.getWidth();
            containerHeight = mapContainer.getHeight();
            imageWidth = mapImage.getDrawable().getIntrinsicWidth();
            imageHeight = mapImage.getDrawable().getIntrinsicHeight();
        });

        // Cargar la imagen del mapa
        mapImage.setImageResource(R.drawable.mapa_madrid);


        scaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        // Establecer la imagen en su posición inicial centrada
        centerImage();

        // Evento de toque para manejar el desplazamiento y el zoom
        mapImage.setOnTouchListener((View v, MotionEvent event) -> {
            final ImageView view = (ImageView) v;
            view.setScaleType(ImageView.ScaleType.MATRIX);
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    savedMatrix.set(matrix);
                    start.set(event.getX(), event.getY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;

                    // Verificar los límites antes de aplicar la traducción
                    matrix.getValues(matrixValues);
                    float currentX = matrixValues[Matrix.MTRANS_X];
                    float currentY = matrixValues[Matrix.MTRANS_Y];

                    if (isWithinBounds(currentX + dx, currentY + dy)) {
                        matrix.postTranslate(dx, dy);
                    } else {
                        Toast.makeText(MapActivity.this, "No se puede mover más allá del límite", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    savedMatrix.set(matrix);
                    break;
            }
            view.setImageMatrix(matrix);

            // Mantener todos los puntos de reciclaje en sus posiciones correctas
            updateAllRecyclingPoints();

            return true; // Evento manejado
        });

        // Boton para centrar la imagen en su posicion inicial
        centerButton.setOnClickListener(v -> centerImage());

        // Boton para agregar un punto de reciclaje en la posicion del cursor
        addRecycleButton.setOnClickListener(v -> {
            float[] cursorPosition = getCursorPositionOnMap();
            addRecyclingPoint(cursorPosition[0], cursorPosition[1]);
        });
    }

    // Listener para gestionar el zoom
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            final float scaleFactor = detector.getScaleFactor();
            scale *= scaleFactor;
            scale = Math.max(1f, Math.min(scale, 5f)); // Limitar el zoom entre 1x y 5x
            matrix.setScale(scale, scale);
            mapImage.setImageMatrix(matrix);

            // Actualizar todas las posiciones de los puntos de reciclaje
            updateAllRecyclingPoints();
            return true;
        }
    }

    // Metodo para centrar la imagen en su posición inicial
    private void centerImage() {
        matrix.reset();
        final float offsetX = (containerWidth - imageWidth * scale) / 2;
        final float offsetY = (containerHeight - imageHeight * scale) / 2;
        matrix.postTranslate(offsetX, offsetY);
        mapImage.setImageMatrix(matrix);

        // Si hay puntos de reciclaje, actualizar sus posiciones
        updateAllRecyclingPoints();
    }

    // Metodo para agregar un punto de reciclaje en la posicion especificada dentro de la imagen
    private void addRecyclingPoint(final float imageX, final float imageY) {
        ImageView recyclingPoint = new ImageView(this);
        recyclingPoint.setImageResource(R.drawable.ic_recycling); // Asegúrate de tener el recurso de reciclaje en drawable
        final int size = 50;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size);
        recyclingPoint.setLayoutParams(params);

        // Añadir el punto de reciclaje al contenedor
        mapContainer.addView(recyclingPoint);

        // Guardar el punto de reciclaje y su posición original
        recyclingPoints.add(recyclingPoint);
        recyclingPointPositions.add(new float[]{imageX, imageY});

        // Establecer la posición del punto de reciclaje
        setRecyclingPointPosition(recyclingPoint, imageX, imageY);
    }

    // Metodo para obtener la posición del cursor relativa a la imagen del mapa
    private float[] getCursorPositionOnMap() {
        float[] cursorPosition = new float[2];
        float cursorCenterX = cursorImage.getX() + (float) cursorImage.getWidth() / 2;
        float cursorCenterY = cursorImage.getY() + (float) cursorImage.getHeight() / 2;
        matrix.invert(matrix); // Invertir la matriz para obtener coordenadas de la imagen original
        matrix.mapPoints(cursorPosition, new float[]{cursorCenterX, cursorCenterY});
        matrix.invert(matrix); // Revertir la inversión de la matriz
        return cursorPosition;
    }

    // Metodo para establecer la posición del punto de reciclaje sobre la imagen
    private void setRecyclingPointPosition(ImageView recyclingPoint, final float imageX, final float imageY) {
        float[] point = new float[]{imageX, imageY};
        matrix.mapPoints(point);
        recyclingPoint.setX(point[0] - (float) recyclingPoint.getWidth() / 2);
        recyclingPoint.setY(point[1] - (float) recyclingPoint.getHeight() / 2);
    }

    // Metodo para actualizar las posiciones de todos los puntos de reciclaje cuando la imagen se mueve o hace zoom
    private void updateAllRecyclingPoints() {
        for (int i = 0; i < recyclingPoints.size(); i++) {
            float[] position = recyclingPointPositions.get(i);
            ImageView recyclingPoint = recyclingPoints.get(i);
            setRecyclingPointPosition(recyclingPoint, position[0], position[1]);
        }
    }

    // Verificar si la imagen está dentro de los límites del contenedor
    private boolean isWithinBounds(final float newX, final float newY) {
        final float scaledWidth = imageWidth * scale;
        final float scaledHeight = imageHeight * scale;

        // Ajustar los límites para que no se pueda mover más allá de los bordes del contenedor
        boolean withinX = newX <= 0 && newX >= containerWidth - scaledWidth;
        boolean withinY = newY <= 0 && newY >= containerHeight - scaledHeight;

        return withinX && withinY;
    }
}



