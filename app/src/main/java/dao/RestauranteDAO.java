package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

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
            values.put("descricaoRestaurante",restaurante.getDescricaoRestaurnate());
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
            values.put("descricao",restaurante.getDescricaoRestaurnate());
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

    // listar dados do restaurante
    public Restaurante listarDados(int id){
        Restaurante restaurante = null;
        try {
            // abrir banco de dados para leitura
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM restaurante WHERE idRestaurante " +
                    "= ? ", new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()){
                restaurante = new Restaurante();
                restaurante.setIdRestaurante(cursor.getInt(cursor.getColumnIndexOrThrow("idRestaurante")));
                restaurante.setNomeRestaurante(cursor.getString(cursor.getColumnIndexOrThrow("nomeRestaurante")));
                restaurante.setEnderecoRestaurante(cursor.getString(cursor.getColumnIndexOrThrow("enderecoRestaurante")));
                restaurante.setTelefoneRestaurante(cursor.getString(cursor.getColumnIndexOrThrow("telefoneRestaurante")));
                restaurante.setDescricaoRestaurante(cursor.getString(cursor.getColumnIndexOrThrow("descricaoRestaurante")));
                restaurante.setHorarioFuncionamento(cursor.getString(cursor.getColumnIndexOrThrow("horarioFuncionamento")));
            }

            // fechar o cursor
            cursor.close();
            // fechar a conexao com o banco de dados
            database.close();

        } catch (Exception e){
            Log.e("UsuarioDAO", "erro ao listar restaurante"+e.getMessage());
        }
        return restaurante;
    }

    // listar todos os restaurantes
    public ArrayList<Restaurante> listarRestaurantes(){
        ArrayList<Restaurante> restaurantes = new ArrayList<>();
        try {
            // abrir banco de dados para leitura
            database = dbHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM restaurante ",
                     null);

            if (cursor.moveToFirst()){
                do {
                    Restaurante restaurante  = new Restaurante();
                    restaurante.setIdRestaurante(cursor.getInt(cursor.getColumnIndexOrThrow("idRestaurante")));
                    restaurante.setNomeRestaurante(cursor.getString(cursor.getColumnIndexOrThrow("nomeRestaurante")));
                    restaurante.setEnderecoRestaurante(cursor.getString(cursor.getColumnIndexOrThrow("enderecoRestaurante")));
                    restaurante.setTelefoneRestaurante(cursor.getString(cursor.getColumnIndexOrThrow("telefoneRestaurante")));
                    restaurante.setDescricaoRestaurante(cursor.getString(cursor.getColumnIndexOrThrow("descricaoRestaurante")));
                    restaurante.setHorarioFuncionamento(cursor.getString(cursor.getColumnIndexOrThrow("horarioFuncionamento")));

                    restaurantes.add(restaurante);
                } while (cursor.moveToNext());
            }

            // fechar o cursor
            cursor.close();
            // fechar a conexao com o banco de dados
            database.close();

        } catch (Exception e){
            Log.e("UsuarioDAO", "erro ao listar todos os restaurantes"+e.getMessage());
        }
        return restaurantes;
    }
}

