package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView nextCollectionText;
    private Button setReminderButton;
    private Calendar selectedDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_calendario);

        calendarView = findViewById(R.id.calendarView);
        nextCollectionText = findViewById(R.id.nextCollectionText);
        setReminderButton = findViewById(R.id.setReminderButton);

        // Actualizar la vista de texto cuando se selecciona una fecha
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            nextCollectionText.setText("Próxima recolección: " + dateFormat.format(selectedDate.getTime()));
        });

        // Agregar evento al botón para configurar un recordatorio
        setReminderButton.setOnClickListener(v -> setReminder());
    }

    private void setReminder() {
        // Crear un Intent para el recordatorio
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Obtener el AlarmManager y configurar la alarma para la fecha seleccionada
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, selectedDate.getTimeInMillis(), pendingIntent);
        }
    }
}
