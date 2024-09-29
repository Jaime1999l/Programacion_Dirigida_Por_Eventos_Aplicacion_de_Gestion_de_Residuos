package com.example.myapplication_11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Asegúrate de que esté vinculado a activity_main.xml

        // Saludo personalizado
        TextView greetingTextView = findViewById(R.id.greeting_text_view);
        greetingTextView.setText("¡Bienvenido a la aplicación de recogida de residuos!");

        // Botón para ir al Calendario
        Button calendarButton = findViewById(R.id.calendar_button);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        // Botón para ir al Mapa
        Button mapButton = findViewById(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        // Botón para ir a Estadísticas
        Button statsButton = findViewById(R.id.stats_button);
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatsActivity.class);
                startActivity(intent);
            }
        });

        // Si necesitas añadir lógica para la imagen (aunque en este caso no es necesario)
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.reciclaje);
        // Puedes agregar algún comportamiento si es necesario
    }
}
