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
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos.Residuos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private TextView nextCollectionText;
    private EditText reminderText;
    private TextView residuosTextView; // Vinculación de residuosTextView
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
        residuosTextView = findViewById(R.id.residuosTextView); // Conexión del TextView

        database = DatabaseResiduos.getDatabase(this);

        // Actualizar la vista de texto cuando se selecciona una fecha
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(selectedDate.getTime());
            nextCollectionText.setText("Próxima recolección: " + formattedDate);

            // Cargar y mostrar recordatorios para la fecha seleccionada
            loadRemindersForSelectedDate();

            // Mostrar residuos depositados para la fecha seleccionada
            mostrarResiduosPorFecha(formattedDate); // Llamada al método para mostrar los residuos
        });

        // Agregar evento al botón para configurar un recordatorio
        setReminderButton.setOnClickListener(v -> saveReminder());
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

    // Método para mostrar los residuos depositados en la fecha seleccionada
    private void mostrarResiduosPorFecha(String fechaSeleccionada) {
        // Asegúrate de que el formato de la fecha sea el mismo que el utilizado en la base de datos
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(selectedDate.getTime()); // Convertimos la fecha seleccionada a String

        AsyncTask.execute(() -> {
            // Usamos la fecha formateada para obtener los residuos
            List<Residuos> residuosList = database.residuosDao().obtenerResiduosPorFecha(formattedDate);

            runOnUiThread(() -> {
                if (!residuosList.isEmpty()) {
                    StringBuilder residuosInfo = new StringBuilder("Residuos depositados:\n");
                    for (Residuos residuos : residuosList) {
                        residuosInfo.append("Bolsas: ").append(residuos.getBolsas())
                                .append(" Contenedor: ").append(residuos.getTipoContenedor())
                                .append("\n");
                    }
                    residuosTextView.setText(residuosInfo.toString()); // Actualizamos el TextView con los residuos
                } else {
                    residuosTextView.setText("No hay residuos depositados en esta fecha");
                }
            });
        });
    }
}