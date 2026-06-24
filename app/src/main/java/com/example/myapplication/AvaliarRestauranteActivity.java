package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityAvaliarRestauranteBinding;

import controller.RestauranteController;
import model.Restaurante;

public class AvaliarRestauranteActivity extends AppCompatActivity {
    ActivityAvaliarRestauranteBinding binding;
    RestauranteController restauranteController;
    Restaurante restaurante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAvaliarRestauranteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        restauranteController = new RestauranteController(AvaliarRestauranteActivity.this);
        binding.imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvaliarRestauranteActivity.this, ListarRestaurantesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.btnAvaliarRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}