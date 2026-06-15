package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityListarRestaurantesBinding;

import java.util.ArrayList;

import adapter.RestauranteAdapter;
import controller.RestauranteController;
import model.Restaurante;

public class ListarRestaurantesActivity extends AppCompatActivity {
    ActivityListarRestaurantesBinding binding;
    Restaurante restaurante;
    RestauranteController controller;
    ArrayList<Restaurante> restaurantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListarRestaurantesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        controller = new RestauranteController(this);
        restaurantes = controller.listarRestaurantes();

        if (restaurantes.isEmpty()){
            Intent tela = new Intent(ListarRestaurantesActivity.this, CadastroDeRestauranteActivity.class);
            startActivity(tela);
            finish();
        }

        RestauranteAdapter adapter = new RestauranteAdapter(this, restaurantes);
        binding.listaRestaurantes.setAdapter(adapter);

    }
}