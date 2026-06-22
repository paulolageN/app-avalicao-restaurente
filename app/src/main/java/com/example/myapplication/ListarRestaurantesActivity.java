package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityListarRestaurantesBinding;

import java.util.ArrayList;

import adapter.RestauranteAdapter;
import controller.RestauranteController;
import controller.UsuarioController;
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
        binding.listaRestaurantes.setLongClickable(true);

        binding.listaRestaurantes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListarRestaurantesActivity.this);
                builder.setTitle("Confirmar exclusao do restaurante");
                builder.setMessage("Tem certeza que deseja excluir o seu perfil?");
                builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controller = new RestauranteController(ListarRestaurantesActivity.this);
                        controller.excluir(restaurante);
                        Toast.makeText(ListarRestaurantesActivity.this, "Perfil excluido com sucesso", Toast.LENGTH_LONG).show();

                        // refireciona para a tela de login
                        Intent intent = new Intent(ListarRestaurantesActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("Cancelar", null);
                builder.show();


                return true;
            }
        });

    }
}