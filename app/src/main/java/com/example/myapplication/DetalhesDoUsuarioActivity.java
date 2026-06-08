package com.example.myapplication;

import static android.app.ProgressDialog.show;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityDetalhesDoUsuarioBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import controller.UsuarioController;
import model.Usuario;

public class DetalhesDoUsuarioActivity extends AppCompatActivity {
    ActivityDetalhesDoUsuarioBinding binding;
    UsuarioController usuarioController;
    Usuario user;

    static final int REQUEST_GALERIA = 1;
    static final int REQUEST_CAMERA = 3;
    static final int REQUEST_PERMISSAO = 2;

    Uri uriImagem;

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

        verificarPermissaoGaleria();
        binding.btnEditarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoCameraGaleria();
            }
        });

    }

    // verifica se o app tem permisao para acessar a galeria
    public void verificarPermissaoGaleria(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // apresenta uma menssagem para o usuario
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                new AlertDialog.Builder(this)
                        .setTitle("Permissao necessaria")
                        .setMessage("AvaliaFood deseja acessar sua galeria de fotos")
                        .setPositiveButton("Permitir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(DetalhesDoUsuarioActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSAO);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();


            } else {
                // solicita diretamente caso nunca foi pedido o acesso a galeria
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSAO);
            }
        }
    }

    // metodo para perguntar se o usuario quer abrir a camera ou nao
    public void mostrarDialogoCameraGaleria(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesDoUsuarioActivity.this);
        builder.setTitle("Selecionar a foto do perfil");
        String[] opcoes = {"Abirir a Galeria de Fotos", "Abirir Camera"};

        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    // abrir a galeria
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_GALERIA);
                } else {
                    // abrir a camera
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null){
                        File foto = null;

                        try {
                            foto = criaArquivoImagem();
                        }catch (IOException e){
                            Toast.makeText(DetalhesDoUsuarioActivity.this, "Erro ao selecionar a foto", Toast.LENGTH_LONG).show();
                            return;
                        }
                        // verifica se tem foto
                        if (foto != null){
                            uriImagem = androidx.core.content.FileProvider.getUriForFile(DetalhesDoUsuarioActivity.this, getOpPackageName()+".provider", foto);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImagem);
                            // permissao temporaria de escrita na galeria
                            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            startActivityForResult(intent, REQUEST_PERMISSAO);
                        }
                    } else {
                        Toast.makeText(DetalhesDoUsuarioActivity.this, "Nenhuma camera foi encontrada", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    // metodo para criar o arquivo da foto
    public File criaArquivoImagem() throws IOException{
        // criar um unico arquivo
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String nomeFoto = "JPGE_" + time + "_";
        // pega o diretorio da foto
        File diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // cria a foto fisica
        File foto = File.createTempFile(nomeFoto, ".jpg", diretorio);

        return foto;
    }
}