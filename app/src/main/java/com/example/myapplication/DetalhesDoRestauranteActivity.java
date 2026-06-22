package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityDetalhesDoRestauranteBinding;

import model.Restaurante;

public class DetalhesDoRestauranteActivity extends AppCompatActivity {

    ActivityDetalhesDoRestauranteBinding binding;
    Restaurante restaurante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalhesDoRestauranteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        restaurante = (Restaurante) getIntent().getSerializableExtra("restaurante");
        binding.txtNomeRestaurante.setText("Restaurante"+restaurante.getNomeRestaurante());
        binding.txtEnderecoRestaurante.setText("Endereco"+restaurante.getEnderecoRestaurante());
        binding.txtTelefone.setText("Telefone"+restaurante.getTelefoneRestaurante());
        binding.txtDescricaoRestaurante.setText("Descricao"+restaurante.getDescricaoRestaurnate());
        binding.txtHorarioRestaurante.setText("Horario de funcionamento"+restaurante.getHorarioFuncionamento());

        binding.imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesDoRestauranteActivity.this, ListarRestaurantesActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}