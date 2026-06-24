package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityCadastroDeRestauranteBinding;

import controller.RestauranteController;
import model.Restaurante;
import model.Usuario;

public class CadastroDeRestauranteActivity extends AppCompatActivity {
    ActivityCadastroDeRestauranteBinding binding;
    RestauranteController restauranteController;
    Restaurante restaurante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroDeRestauranteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        restauranteController = new RestauranteController(CadastroDeRestauranteActivity.this);

        binding.btnCadstrarRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurante = new Restaurante();
                restaurante.setNomeRestaurante(binding.editNomeRestaurante.getText().toString());
                restaurante.setEnderecoRestaurante(binding.editEnderecoRestaurante.getText().toString());
                restaurante.setTelefoneRestaurante(binding.editTelefoneRestaurante.getText().toString());
                restaurante.setDescricaoRestaurante(binding.editDescricao.getText().toString());
                restaurante.setHorarioFuncionamento(binding.editHorarioRestaurante.getText().toString());

                if(restauranteController.inserir(restaurante)){
                    Toast.makeText(CadastroDeRestauranteActivity.this,"Seu cadastro foi realizado com sucesso!", Toast.LENGTH_LONG).show();
                    //Chamar a proxima tela principal
                    Intent intent = new Intent(CadastroDeRestauranteActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(CadastroDeRestauranteActivity.this,"Erro ao realizar o seu cadastro!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}