package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.R;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.database.DatabaseResiduos;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.recordatorios.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private TextView nextCollectionText;
    private EditText reminderText;
    private final Calendar selectedDate = Calendar.getInstance();
    private DatabaseResiduos database;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_calendario);

        CalendarView calendarView = findViewById(R.id.calendarView);
        nextCollectionText = findViewById(R.id.nextCollectionText);
        Button setReminderButton = findViewById(R.id.setReminderButton);
        reminderText = findViewById(R.id.reminderText);

        database = DatabaseResiduos.getDatabase(this);

        // Actualizar la vista de texto cuando se selecciona una fecha
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            nextCollectionText.setText("Próxima recolección: " + dateFormat.format(selectedDate.getTime()));

            // Cargar y mostrar recordatorios para la fecha seleccionada
            loadRemindersForSelectedDate();
        });

        // Agregar evento al botón para configurar un recordatorio
        setReminderButton.setOnClickListener(v -> {
            saveReminder();
        });
    }

    private void saveReminder() {
        String reminderDescription = reminderText.getText().toString();
        if (!reminderDescription.isEmpty()) {
            Reminder reminder = new Reminder(selectedDate.getTimeInMillis(), reminderDescription);

            // Guardar el recordatorio en la base de datos
            AsyncTask.execute(() -> {
                database.reminderDao().insert(reminder);
            });

            reminderText.setText(""); // Limpiar el campo de texto
            Toast.makeText(this, "Recordatorio guardado", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRemindersForSelectedDate() {
        AsyncTask.execute(() -> {
            long selectedDateInMillis = selectedDate.getTimeInMillis();
            List<Reminder> reminders = database.reminderDao().getRemindersByDate(selectedDateInMillis);

            runOnUiThread(() -> {
                if (!reminders.isEmpty()) {
                    // Mostrar un aviso abajo si hay recordatorios para la fecha seleccionada
                    Toast.makeText(this, "Tienes " + reminders.size() + " recordatorio(s) para este día", Toast.LENGTH_SHORT).show();
                }

                // Actualizar el TextView con los recordatorios
                StringBuilder remindersText = new StringBuilder("Recordatorios:\n");
                for (Reminder reminder : reminders) {
                    remindersText.append(reminder.getDescription()).append("\n");
                }
                nextCollectionText.setText(remindersText.toString());
            });
        });
    }
}

