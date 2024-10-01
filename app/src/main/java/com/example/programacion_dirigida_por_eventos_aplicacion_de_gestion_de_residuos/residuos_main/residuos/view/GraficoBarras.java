package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos_main.residuos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

public class GraficoBarras extends View {

    private List<Float> data;
    private List<Integer> colors;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GraficoBarras(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // Método para establecer los datos y colores de las barras
    public void setData(List<Float> data, List<Integer> colors) {
        this.data = data;
        this.colors = colors;
        invalidate();  // Redibujar la vista
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data == null || colors == null || data.size() != colors.size()) {
            return;
        }

        float max = 0;
        for (float value : data) {
            if (value > max) {
                max = value;
            }
        }

        int width = getWidth();
        int height = getHeight();
        int barWidth = width / data.size();

        // Dibujar cada barra con su color correspondiente
        for (int i = 0; i < data.size(); i++) {
            paint.setColor(colors.get(i));
            float barHeight = (data.get(i) / max) * height;
            canvas.drawRect(i * barWidth, height - barHeight, (i + 1) * barWidth, height, paint);
        }
    }
}
