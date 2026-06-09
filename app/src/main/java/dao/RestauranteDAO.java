package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import database.BDHelper;
import model.Restaurante;
import model.Usuario;

public class RestauranteDAO {
    // atributo para realizar operacoes no db
    private SQLiteDatabase database;
    // atributo da classe DBHelper
    private BDHelper dbHelper;

    public RestauranteDAO(Context context){
        dbHelper = new BDHelper(context);
    }

    // metodo para inserir dados na tabela restaurante
    public boolean inserir(Restaurante restaurante){
        try {
            // abrir banco de dados para salvar dados na tabela restaurante
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            // nome do campo da tabela, valor
            values.put("nomeRestaurante",restaurante.getNomeRestaurante());
            values.put("enderecoRestaurante",restaurante.getEnderecoRestaurante());
            values.put("telefoneRestaurante",restaurante.getTelefoneRestaurante());
            values.put("descricao",restaurante.getDescricao());
            values.put("horarioFuncionamento",restaurante.getHorarioFuncionamento());

            long result = database.insert("restaurante", null,values);

            // fechar a conexao com o banco de dados
            database.close();


            if (result == -1){
                Log.i("INFO DB", "ERRO AO INSERIR O RESTAURANTE");
                return false;
            } else{
                Log.i("INFO DB", "SUCESSO AO INSERIR O RESTAURANTE");
                return true;
            }
        } catch (Exception e){
            Log.e("UsuarioDAO", "erro ao inserir restaurante"+e.getMessage());
            return false;
        }
    }

    // metodo para alterar dados na tabela restaurante
    public boolean alterar(Restaurante restaurante){
        try {
            // abrir banco de dados para salvar dados na tabela restaurante
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            // nome do campo da tabel, valor
            values.put("nomeRestaurante",restaurante.getNomeRestaurante());
            values.put("enderecoRestaurante",restaurante.getEnderecoRestaurante());
            values.put("telefoneRestaurante",restaurante.getTelefoneRestaurante());
            values.put("descricao",restaurante.getDescricao());
            values.put("horarioFuncionamento",restaurante.getHorarioFuncionamento());;

            long result = database.update("restaurante", values, "idRestaurante = ?", new String[]{String.valueOf(restaurante.getIdRestaurante())});

            // fechar a conexao com o banco de dados
            database.close();


            if (result <= 0){
                Log.i("INFO DB", "ERRO AO ALTERAR OS DADOS DO RESTAURANTE");
                return false;
            } else{
                Log.i("INFO DB", "SUCESSO AO ALTERAR OS DADOS DO RESTAURANTE");
                return true;
            }
        } catch (Exception e){
            Log.e("RestauranteDAO", "erro ao alterar os dados do restaurante"+e.getMessage());
            return false;
        }
    }

    // metodo para excluir restaurantes
    public boolean deletar(Restaurante restaurante){
        try {
            // abrir banco de dados para deletar restaurante
            database = dbHelper.getWritableDatabase();
            // deleta um restaurante
            long result = database.delete("restaurante", "idRestaurante = ?", new String[]{String.valueOf(restaurante.getIdRestaurante())});

            // fechar a conexao com o banco de dados
            database.close();


            if (result == -1){
                Log.i("INFO DB", "ERRO AO DELETAR O RESTAURANTE");
                return false;
            } else{
                Log.i("INFO DB", "SUCESSO AO DELETAR O RESTAURANTE");
                return true;
            }
        } catch (Exception e){
            Log.e("RestauranteDAO", "erro ao DELETAR restaurante"+e.getMessage());
            return false;
        }
    }

    // pegar dados do restaurante
    public Restaurante listarDados(int id){
        Restaurante restaurante = null;
        try {
            // abrir banco de dados para leitura
            database = dbHelper.getReadableDatabase();
            // deleta um usuario
            Cursor cursor = database.rawQuery("SELECT * FROM restaurante WHERE idRestaurante " +
                    "= ? ", new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()){
                restaurante = new Restaurante();
                restaurante.setIdRestaurante(cursor.getInt(cursor.getColumnIndexOrThrow("idRestaurante")));
                restaurante.setIdRestaurante(cursor.getInt(cursor.getColumnIndexOrThrow("nomeRestaurante")));
                restaurante.setIdRestaurante(cursor.getInt(cursor.getColumnIndexOrThrow("enderecoRestaurante")));
                restaurante.setIdRestaurante(cursor.getInt(cursor.getColumnIndexOrThrow("telefoneRestaurante")));
                restaurante.setIdRestaurante(cursor.getInt(cursor.getColumnIndexOrThrow("descricaoRestaurante")));
                restaurante.setIdRestaurante(cursor.getInt(cursor.getColumnIndexOrThrow("horarioFuncionamento")));
            }

            // fechar o cursor
            cursor.close();
            // fechar a conexao com o banco de dados
            database.close();

        } catch (Exception e){
            Log.e("UsuarioDAO", "erro ao listar dados usuario"+e.getMessage());
        }
        return restaurante;
    }
}

