package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import database.BDHelper;
import model.Usuario;

public class UsuarioDAO {
    // atributo para realizar operacoes no db
    private SQLiteDatabase database;
    // atributo da classe DBHelper
    private BDHelper dbHelper;

    public UsuarioDAO(Context context){
        dbHelper = new BDHelper(context);
    }

    // metodo para inserir dados na tabela usuarios
    public boolean inserir(Usuario usuario){
        try {
            // abrir banco de dados para salvar dados na tabela usuario
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            // nome do campo da tabel, valor
            values.put("nomeUsuario",usuario.getNomeUsuario());
            values.put("emailUsuario",usuario.getEmailUsuario());
            values.put("senhaUsuario",usuario.getSenhaUsuario());
            values.put("fotoUsuario",usuario.getFotoUsuario());

            long result = database.insert("usuario", null,values);

            // fechar a conexao com o banco de dados
            database.close();


            if (result == -1){
                Log.i("INFO DB", "ERRO AO INSERIR O USUARIO");
                return false;
            } else{
                Log.i("INFO DB", "SUCESSO AO INSERIR O USUARIO");
                return true;
            }
        } catch (Exception e){
            Log.e("UsuarioDAO", "erro ao inserir usuario"+e.getMessage());
            return false;
        }
    }

    // metodo para alterar dados na tabela usuarios
    public boolean alterar(Usuario usuario){
        try {
            // abrir banco de dados para salvar dados na tabela usuario
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            // nome do campo da tabel, valor
            values.put("nomeUsuario",usuario.getNomeUsuario());
            values.put("emailUsuario",usuario.getEmailUsuario());
            values.put("senhaUsuario",usuario.getSenhaUsuario());
            values.put("fotoUsuario",usuario.getFotoUsuario());

            long result = database.update("usuario", values, "idUsuario = ?", new String[]{String.valueOf(usuario.getIdUsuario())});

            // fechar a conexao com o banco de dados
            database.close();


            if (result <= 0){
                Log.i("INFO DB", "ERRO AO ALTERAR OS DADOS DO USUARIO");
                return false;
            } else{
                Log.i("INFO DB", "SUCESSO AO ALTERAR OS DADOS DO USUARIO");
                return true;
            }
        } catch (Exception e){
            Log.e("UsuarioDAO", "erro ao alterar os dados do usuario"+e.getMessage());
            return false;
        }
    }

    // metodo para excluir usuarios
    public boolean deletar(Usuario usuario){
        try {
            // abrir banco de dados para deletar usuario
            database = dbHelper.getWritableDatabase();
            // deleta um usuario
            long result = database.delete("usuario", "idUsuario = ?", new String[]{String.valueOf(usuario.getIdUsuario())});

            // fechar a conexao com o banco de dados
            database.close();


            if (result == -1){
                Log.i("INFO DB", "ERRO AO DELETAR O USUARIO");
                return false;
            } else{
                Log.i("INFO DB", "SUCESSO AO DELETAR O USUARIO");
                return true;
            }
        } catch (Exception e){
            Log.e("UsuarioDAO", "erro ao DELETAR usuario"+e.getMessage());
            return false;
        }
    }

    // metodo para validar usuarios (login)
    public Usuario login(String email, String senha){
        Usuario usuario = null;
        try {
            // abrir banco de dados para leitura
            database = dbHelper.getReadableDatabase();
            // deleta um usuario
            Cursor cursor = database.rawQuery("SELECT * FROM usuario WHERE emailUsuario " +
                    "= ? AND senhaUsuario = ?", new String[]{email, senha});

            if (cursor.moveToFirst()){
                usuario = new Usuario();
                usuario.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario")));
                usuario.setNomeUsuario(cursor.getString(cursor.getColumnIndexOrThrow("nomeUsuario")));
                usuario.setFotoUsuario(cursor.getString(cursor.getColumnIndexOrThrow("fotoUsuario")));
                usuario.setEmailUsuario(cursor.getString(cursor.getColumnIndexOrThrow("emailUsuario")));
                usuario.setSenhaUsuario(cursor.getString(cursor.getColumnIndexOrThrow("senhaUsuario")));
            }

            // fechar o cursor
            cursor.close();
            // fechar a conexao com o banco de dados
            database.close();

        } catch (Exception e){
            Log.e("UsuarioDAO", "erro ao deletar usuario"+e.getMessage());
        }
        return usuario;
    }
}
