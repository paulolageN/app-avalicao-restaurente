package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityDetalhesDoUsuarioBinding;

import controller.UsuarioController;
import model.Usuario;

public class DetalhesDoUsuarioActivity extends AppCompatActivity {
    ActivityDetalhesDoUsuarioBinding binding;
    UsuarioController usuarioController;
    Usuario user;

    static final int REQUEST_GALERIA = 1;
    static final int REQUEST_CAMERA = 1;
    static final int REQUEST_PERMISSAO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalhesDoUsuarioBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // pega o id do usuario logado
        SharedPreferences preferences = getSharedPreferences("usuario", MODE_PRIVATE);
        int idUsuario = preferences.getInt("id", -1);

        // se nao tem usuario logado
        if (idUsuario == -1){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            usuarioController = new UsuarioController(DetalhesDoUsuarioActivity.this);
            user = usuarioController.listarDados(idUsuario);
            binding.editNomeUsuario.setText(user.getNomeUsuario());
            binding.editEmailUsuario.setText(user.getEmailUsuario());
            binding.editSenhaUsuario.setText(user.getSenhaUsuario());
            // verificar se tem foto de perfil
            Bitmap bitmap = BitmapFactory.decodeFile(user.getFotoUsuario());
            binding.fotoPerfil.setImageBitmap(bitmap);

        }
    }
}