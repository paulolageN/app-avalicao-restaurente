package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class BDHelper extends SQLiteOpenHelper {
    // atributo para definir o nome do banco de dados
    private static final String DB_NOME = "apprestaurante.db";
    // atributo para definir a versao do banco de dados
    private static final int DB_VERSAO = 1;
    public BDHelper(@Nullable Context context) {
        super(context, DB_NOME, null, DB_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            // cria as tabelas do db
            db.execSQL("CREATE TABLE usuario(idUsuario INTEGER PRIMARY KEY " +
                        "AUTOINCREMENT, nomeUsuario TEXT NOT NULL," +
                        "emailUsuario TEXT NOT NULL, fotoUsuario TEXT)");

        }catch (Exception e){
            // apresenta o erro no logcat
            e.printStackTrace();
            Log.e("DBHelper", "Erro ao criar as tabelas: "+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       try{
           // exclui as tabelas do banco de dados
           db.execSQL("DROP TABLE IF EXISTS usuario");
       } catch (Exception e){
           // apresenta o erro no logcat
           e.printStackTrace();
           Log.e("DBHelper", "Erro ao excluir as tabelas: "+e.getMessage());
       }

    }
}
