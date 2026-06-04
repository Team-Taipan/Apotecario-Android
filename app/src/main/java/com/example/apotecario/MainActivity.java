package com.example.apotecario;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); 
            return insets;
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        
        // Define a tela de Início como padrão ao abrir o app
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new InicioFragment())
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_inicio) {
                selectedFragment = new InicioFragment();
            } else if (id == R.id.nav_agenda) {
                // selectedFragment = new AgendaFragment();
                selectedFragment = new InicioFragment(); // Placeholder
            } else if (id == R.id.nav_progresso) {
                // selectedFragment = new ProgressoFragment();
                selectedFragment = new InicioFragment(); // Placeholder
            } else if (id == R.id.nav_config) {
                // selectedFragment = new ConfigFragment();
                selectedFragment = new InicioFragment(); // Placeholder
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }
}