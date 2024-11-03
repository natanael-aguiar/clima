package com.clima.clima;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private MapFragment mapFragment;
    private WeatherFragment weatherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar a Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar o DrawerLayout e o NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Configurar o ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));

        // Configurar ações de clique nos itens do menu do NavigationView
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, "Início selecionado", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_about) {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Item não reconhecido", Toast.LENGTH_SHORT).show();
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // Configurar TabLayout e ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        // Configurar adaptador do ViewPager
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        weatherFragment = new WeatherFragment(); // Fragment da primeira aba
                        return weatherFragment;
                    case 1:
                        mapFragment = new MapFragment(); // Fragment da segunda aba (mapa)
                        return mapFragment;
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2; // Número de abas
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Clima";
                    case 1:
                        return "Mapa";
                    default:
                        return null;
                }
            }
        });

        // Vincular o TabLayout ao ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // Configurar o FloatingActionButton para iniciar o escaneamento de QR Code
        FloatingActionButton fabQrCode = findViewById(R.id.fab_qr_code);
        fabQrCode.setOnClickListener(v -> new IntentIntegrator(MainActivity.this).initiateScan());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Capturar o resultado do QR Code
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            try {
                // Supondo que o QR Code contenha "latitude,longitude"
                String[] coordinates = result.getContents().split(",");
                double latitude = Double.parseDouble(coordinates[0]);
                double longitude = Double.parseDouble(coordinates[1]);

                // Atualizar o MapFragment com a nova localização
                if (mapFragment != null) {
                    mapFragment.updateLocation(latitude, longitude);
                    Toast.makeText(this, "Localização atualizada!", Toast.LENGTH_SHORT).show();
                }

                // Atualizar o WeatherFragment com o `woeid` fixo
                if (weatherFragment != null) {
                    int woeid = 457197; // Código fixo da cidade
                    weatherFragment.fetchWeatherData(woeid);
                    Toast.makeText(this, "Buscando dados de clima para o código fixo", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(this, "Formato de QR Code inválido", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Escaneamento cancelado", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            // Navega para a AboutActivity
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
