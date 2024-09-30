package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.activity.StatsActivity;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.activity.CalendarActivity;
import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.activity.MapActivity;
import com.google.android.material.navigation.NavigationView;

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

        // Configuración del menú desplegable (NavigationView)
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Manejador de clicks para los elementos del menú
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.nav_calendar:
                    Intent calendarIntent = new Intent(PantallaPrincipal.this, CalendarActivity.class);
                    startActivity(calendarIntent);
                    break;
                case R.id.nav_map:
                    Intent mapIntent = new Intent(PantallaPrincipal.this, MapActivity.class);
                    startActivity(mapIntent);
                    break;
                case R.id.nav_stats:
                    Intent statsIntent = new Intent(PantallaPrincipal.this, StatsActivity.class);
                    startActivity(statsIntent);
                    break;
            }
            drawerLayout.closeDrawers(); // Cierra el menú después de hacer clic
            return true;
        });
        // Botón para abrir el menú
        Button openMenuButton = findViewById(R.id.open_menu_button);
        openMenuButton.setOnClickListener(v -> drawerLayout.openDrawer(navigationView));
    }
}
