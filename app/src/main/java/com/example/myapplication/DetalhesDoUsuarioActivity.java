package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

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

    private ActivityResultLauncher<Intent> galeriaLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private String caminhoFotoSelecionada = "user.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalhesDoUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicializarLaunchers();

        //PEGAR O ID DO USUARIO LOGADO
        SharedPreferences preferences = getSharedPreferences("usuario", MODE_PRIVATE);
        int idUsuario = preferences.getInt("id",-1);

        //SE NAO TEM USUÁRIO LOGADO
        if(idUsuario == -1){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            usuarioController = new UsuarioController(DetalhesDoUsuarioActivity.this);
            user = usuarioController.listarDados(idUsuario);
            binding.editNomeUsuario.setText(user.getNomeUsuario());
            binding.editEmailUsuario.setText(user.getEmailUsuario());
            binding.editSenhaUsuario.setText(user.getSenhaUsuario());
            //VERIFICAR SE TEM FOTO DE PERFIL
            if (user.getFotoUsuario() != null && !user.getFotoUsuario().isEmpty()) {
                caminhoFotoSelecionada = user.getFotoUsuario();
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFotoSelecionada);
                if (bitmap != null) {
                    binding.fotoPerfil.setImageBitmap(bitmap);
                }
            }
        }
        verificarPermissaoGaleria();

        binding.btnEditarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoCameraGaleria();
            }
        });

        binding.imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesDoUsuarioActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setNomeUsuario(binding.editNomeUsuario.getText().toString());
                user.setEmailUsuario(binding.editEmailUsuario.getText().toString());
                user.setSenhaUsuario(binding.editSenhaUsuario.getText().toString());
                user.setFotoUsuario(caminhoFotoSelecionada);

                if(usuarioController.alterar(user)){
                    Toast.makeText(DetalhesDoUsuarioActivity.this,"Dados alterados com sucesso!",
                            Toast.LENGTH_LONG).show();
                    //Chamar a proxima tela principal
                    Intent intent = new Intent(DetalhesDoUsuarioActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(DetalhesDoUsuarioActivity.this,"Erro ao atualizar seus dados!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        binding.btnDeletarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(DetalhesDoUsuarioActivity.this);
                builder.setTitle("Confirmar exclusão do perfil");
                builder.setMessage("Tem certeza que deseja excluir o eu perfil? " +
                        "Esta ação não poderá ser desfeita.");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usuarioController = new UsuarioController(DetalhesDoUsuarioActivity.this);
                        usuarioController.excluir(user);
                        Toast.makeText(DetalhesDoUsuarioActivity.this,
                                "Perfil excluido com sucesso", Toast.LENGTH_SHORT).show();
                        //Limpa os dados da sessão
                        SharedPreferences prefs = getSharedPreferences("usuario", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.apply();

                        //Redireciona para a tela de Login
                        Intent intent = new Intent(DetalhesDoUsuarioActivity.this,
                                LoginActivity.class);
                        //Evita que o usuário volta com o botão back do aparelho
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();

            }
        });
    }

    //MÉTODO PARA VERIFICAR SE O APP TEM PERMISSÃO PARA ACESSAR A GALERIA
    public void verificarPermissaoGaleria() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permissão necessária")
                        .setMessage("Precisamos de sua permissão para acessar a galeria de fotos.")
                        .setPositiveButton("Permitir", (dialog, which) ->
                                ActivityCompat.requestPermissions(DetalhesDoUsuarioActivity.this,
                                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSAO))
                        .setNegativeButton("Cancelar", null)
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSAO);
            }
        }
    }

    public void mostrarDialogoCameraGaleria() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecionar a foto do perfil");
        String[] opcoes = {"Abrir a Galeria", "Abrir a Câmera"};

        builder.setItems(opcoes, (dialog, which) -> {
            if (which == 0) {
                // ABRIR GALERIA
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeriaLauncher.launch(intent);
            } else {
                // ABRIR CÂMERA
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File foto = null;
                    try {
                        foto = criarArquivoImagem();
                    } catch (IOException e) {
                        Toast.makeText(DetalhesDoUsuarioActivity.this, "Erro ao criar arquivo de foto", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (foto != null) {
                        caminhoFotoSelecionada = foto.getAbsolutePath(); // Salva o caminho absoluto
                        uriImagem = FileProvider.getUriForFile(DetalhesDoUsuarioActivity.this,
                                getPackageName() + ".provider", foto);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImagem);
                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        cameraLauncher.launch(intent);
                    }
                } else {
                    Toast.makeText(DetalhesDoUsuarioActivity.this, "Nenhuma câmera encontrada", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    public File criarArquivoImagem() throws IOException {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String nomeFoto = "JPEG_" + time + "_";
        File diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(nomeFoto, ".jpg", diretorio);
    }

    /**
     * Converte a URI da galeria no caminho de arquivo físico real do armazenamento
     */
    private String getCaminhoRealDaUri(Uri uri) {
        String caminho = null;

        if (uri == null) {
            return null;
        }

        if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            Cursor cursor = null;

            try {
                String[] projecao = {
                        MediaStore.Images.Media.DATA
                };

                cursor = getContentResolver().query(
                        uri,
                        projecao,
                        null,
                        null,
                        null
                );

                if (cursor != null && cursor.moveToFirst()) {
                    int coluna = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

                    if (coluna != -1) {
                        caminho = cursor.getString(coluna);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        return caminho;
    }
    private void inicializarLaunchers() {
        // Registro do retorno da Galeria
        galeriaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uriSelecionada = result.getData().getData();
                        if (uriSelecionada != null) {
                            caminhoFotoSelecionada = getCaminhoRealDaUri(uriSelecionada);
                            binding.fotoPerfil.setImageURI(uriSelecionada);
                        }
                    }
                }
        );

        // Registro do retorno da Câmera
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // A imagem já foi salva no arquivo físico gerado pela 'uriImagem'
                        binding.fotoPerfil.setImageURI(uriImagem);
                    }
                }
        );
    }

}