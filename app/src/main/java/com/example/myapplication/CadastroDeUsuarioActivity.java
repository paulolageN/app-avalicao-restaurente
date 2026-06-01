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

import com.example.myapplication.databinding.ActivityCadastroDeUsuarioBinding;

import controller.UsuarioController;
import model.Usuario;

public class CadastroDeUsuarioActivity extends AppCompatActivity {
    ActivityCadastroDeUsuarioBinding binding;
    UsuarioController usuarioController;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroDeUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usuarioController = new UsuarioController(CadastroDeUsuarioActivity.this);

        binding.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setNomeUsuario(binding.editNome.getText().toString());
                usuario.setEmailUsuario(binding.editEmail.getText().toString());
                usuario.setSenhaUsuario(binding.editSenha.getText().toString());

                if(usuarioController.inserir(usuario)){
                    Toast.makeText(CadastroDeUsuarioActivity.this,"Seu cadastro foi realizado com sucesso!", Toast.LENGTH_LONG).show();
                    //Chamar a proxima tela principal
                    Intent intent = new Intent(CadastroDeUsuarioActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(CadastroDeUsuarioActivity.this,"Erro ao realizar o seu cadastro!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}