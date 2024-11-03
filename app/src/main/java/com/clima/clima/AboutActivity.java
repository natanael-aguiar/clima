package com.clima.clima;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Configurar o Toolbar como a ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Verificar se a ActionBar foi configurada corretamente e definir o título
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Sobre");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Adiciona o botão de voltar
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Finaliza a Activity ao pressionar o botão de voltar
        return true;
    }
}
