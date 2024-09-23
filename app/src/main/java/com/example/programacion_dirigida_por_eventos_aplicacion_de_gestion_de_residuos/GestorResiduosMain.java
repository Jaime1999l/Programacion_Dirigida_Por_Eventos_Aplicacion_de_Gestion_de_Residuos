package com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.R;

import java.util.Objects;

public class GestorResiduosMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        // Inicializar el botón del menú
        Button menuButton = findViewById(R.id.menu_button);

        // Mostrar el PopupMenu al hacer clic en el botón
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }

    private void showPopupMenu(View view) {
        // Crear un PopupMenu
        PopupMenu popupMenu = new PopupMenu(GestorResiduosMain.this, view);
        // Añadir opciones al menú
        popupMenu.getMenu().add("Calendario"); //No disponible
        popupMenu.getMenu().add("Mapa");
        popupMenu.getMenu().add("Estadísticas"); //No disponible

        // Manejar clics en los elementos del menú
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String title = Objects.requireNonNull(menuItem.getTitle()).toString();

                switch (title) {
                    //case "Calendario":
                    // Navegar a la actividad de Calendario
                    //Intent calendarIntent = new Intent(GestorResiduosMain.this, CalendarActivity.class);
                    //startActivity(calendarIntent);
                    //return true;
                    case "Mapa":
                        // Navegar a la actividad de Mapa
                        Intent mapIntent = new Intent(GestorResiduosMain.this, com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos.activity.MapActivity.class);
                        startActivity(mapIntent);
                        return true;
                    //case "Estadísticas":
                    // Navegar a la actividad de Estadísticas
                    //Intent statsIntent = new Intent(GestorResiduosMain.this, StatsActivity.class);
                    //startActivity(statsIntent);
                    //return true;
                    default:
                        return false;
                }
            }
        });

        // Mostrar el PopupMenu
        popupMenu.show();
    }
}

