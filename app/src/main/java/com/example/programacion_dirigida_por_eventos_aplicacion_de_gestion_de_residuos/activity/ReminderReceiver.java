package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.R;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "RECORDATORIOS_RECOLECCION";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Crear el canal de notificación si es necesario (para Android 8.0 y superior)
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Recordatorios de recolección",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Crear la notificación
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Recordatorio de recolección")
                .setContentText("¡Es hora de sacar la basura o reciclar!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Mostrar la notificación
        notificationManager.notify(0, notificationBuilder.build());
    }
}

