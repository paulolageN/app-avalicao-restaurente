package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityLoginBinding;

import controller.UsuarioController;
import model.Usuario;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    UsuarioController usuarioController
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        preferences = getSharedPreferences("dadosusuario", MODE_PRIVATE);
        verificarLogin();

        usuarioController = new UsuarioController(LoginActivity.this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editEmailLogin.getText().toString();
                String senha = binding.editSenhaLogin.getText().toString();

                Usuario user = usuarioController.login(email, senha);

                if (user != null){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("logado", true);

                    if (binding.switchLogado.isChecked()){
                        editor.putBoolean("manterLogin", true);
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                } else{
                    Toast.makeText(LoginActivity.this, "Usuario ou senha invalidos", Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroDeUsuarioActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // verificar se esta logado ou nao
    private void verificarLogin(){
        boolean manterLogin = preferences.getBoolean("manterLogin", false);
        boolean logado = preferences.getBoolean("logado", false);

        if (manterLogin && logado){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}