package controller;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import dao.RestauranteDAO;
import dao.UsuarioDAO;
import model.Restaurante;
import model.Usuario;


public class RestauranteController {
    private Context context;
    private RestauranteDAO restauranteDAO;

    //METODO CONSTRUTOR
    public RestauranteController(Context context){
        this.context = context;
        this.restauranteDAO = new RestauranteDAO(context);
    }

    public boolean inserir(Restaurante restaurante){
        //IMPLEMENTAR AS REGRAS DE NEGOCIO
        if(restaurante.getNomeRestaurante().isEmpty()){
            Toast.makeText(context, "O campo nome não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(restaurante.getEnderecoRestaurante().isEmpty()){
            Toast.makeText(context, "O campo Endereco não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(restaurante.getTelefoneRestaurante().isEmpty()){
            Toast.makeText(context, "O campo Telefone não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(restaurante.getHorarioFuncionamento().isEmpty()){
            Toast.makeText(context, "O campo horario de funcionamento não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        boolean result = restauranteDAO.inserir(restaurante);
        return result;

    }

    public boolean alterar(Restaurante restaurante){
        //IMPLEMENTAR AS REGRAS DE NEGOCIO
        if(restaurante.getNomeRestaurante().isEmpty()){
            Toast.makeText(context, "O campo nome não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(restaurante.getEnderecoRestaurante().isEmpty()){
            Toast.makeText(context, "O campo Endereco não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(restaurante.getTelefoneRestaurante().isEmpty()) {
            Toast.makeText(context, "O campo Telefone não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(restaurante.getHorarioFuncionamento().isEmpty()){
            Toast.makeText(context, "O campo horario de funcionamento não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        boolean result = restauranteDAO.alterar(restaurante);
        return result;
    }

    public boolean excluir(Restaurante restaurante){
        return restauranteDAO.deletar(restaurante);
    }

    public Restaurante listarDados(int id){
        return restauranteDAO.listarDados(id);
    }

}
