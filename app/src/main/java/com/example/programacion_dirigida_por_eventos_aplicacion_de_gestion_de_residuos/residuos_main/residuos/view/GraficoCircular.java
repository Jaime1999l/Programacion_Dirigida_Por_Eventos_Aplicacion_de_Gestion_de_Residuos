package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos_main.residuos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class GraficoCircular extends View {

    private List<Float> data;
    private List<Integer> colors;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GraficoCircular(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

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

        float total = 0;
        for (float value : data) {
            total += value;
        }

        float startAngle = 0;
        int size = Math.min(getWidth(), getHeight());
        int radius = size / 2;
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        for (int i = 0; i < data.size(); i++) {
            paint.setColor(colors.get(i));
            float sweepAngle = (data.get(i) / total) * 360;
            canvas.drawArc(cx - radius, cy - radius, cx + radius, cy + radius, startAngle, sweepAngle, true, paint);
            startAngle += sweepAngle;
        }
    }
}

