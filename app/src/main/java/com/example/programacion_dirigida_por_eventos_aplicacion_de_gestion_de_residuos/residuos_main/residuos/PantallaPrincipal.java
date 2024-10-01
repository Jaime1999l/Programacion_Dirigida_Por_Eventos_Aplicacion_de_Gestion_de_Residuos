package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos_main.residuos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.R;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos_main.residuos.activity.StatsActivity;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos_main.residuos.activity.CalendarActivity;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.residuos_main.residuos.activity.MapActivity;

public class PantallaPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        // Saludo personalizado
        TextView greetingTextView = findViewById(R.id.greeting_text_view);
        greetingTextView.setText("¡Bienvenido a la aplicación de recogida de residuos!");

        // Imagen centrada
        ImageView imageView = findViewById(R.id.recycling_image);
        imageView.setImageResource(R.drawable.reciclaje);

        // Configuración del menú desplegable (LinearLayout)
        drawerLayout = findViewById(R.id.drawer_layout);

        // Opción del menú: Calendario
        TextView navCalendar = findViewById(R.id.nav_calendar);
        navCalendar.setOnClickListener(v -> {
            Intent calendarIntent = new Intent(PantallaPrincipal.this, CalendarActivity.class);
            startActivity(calendarIntent);
            drawerLayout.closeDrawers(); // Cerrar el menú
        });

        // Opción del menú: Mapa
        TextView navMap = findViewById(R.id.nav_map);
        navMap.setOnClickListener(v -> {
            Intent mapIntent = new Intent(PantallaPrincipal.this, MapActivity.class);
            startActivity(mapIntent);
            drawerLayout.closeDrawers(); // Cerrar el menú
        });

        // Opción del menú: Estadísticas
        TextView navStats = findViewById(R.id.nav_stats);
        navStats.setOnClickListener(v -> {
            Intent statsIntent = new Intent(PantallaPrincipal.this, StatsActivity.class);
            startActivity(statsIntent);
            drawerLayout.closeDrawers(); // Cerrar el menú
        });

        // Botón para abrir el menú
        Button openMenuButton = findViewById(R.id.open_menu_button);
        openMenuButton.setOnClickListener(v -> drawerLayout.openDrawer(findViewById(R.id.menu_layout)));
    }
}
