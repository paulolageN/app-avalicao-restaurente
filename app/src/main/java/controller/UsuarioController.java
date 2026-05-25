package controller;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;
import dao.UsuarioDAO;
import model.Usuario;


public class UsuarioController {
    private Context context;
    private UsuarioDAO usuarioDAO;

    //METODO CONSTRUTOR
    public UsuarioController(Context context){
        this.context = context;
        this.usuarioDAO = new UsuarioDAO(context);
    }

    public boolean inserir(Usuario usuario){
        //IMPLEMENTAR AS REGRAS DE NEGOCIO
        if(usuario.getNomeUsuario().isEmpty()){
            Toast.makeText(context, "O campo nome não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(usuario.getEmailUsuario().isEmpty()){
            Toast.makeText(context, "O campo email não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(usuario.getSenhaUsuario().isEmpty()){
            Toast.makeText(context, "O campo senha não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        String regraSenha = "(?=.*[0-8])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$)[^@\\.;\\(\\)\\_!?&\\-\\+\\^\\´âêôáéíóúÂÊÔÁÉÍÓÚ]+$";
        if(!usuario.getSenhaUsuario().matches(regraSenha)){
            Toast.makeText(context, "O campo senha deve ter no mínimo 8 caracteres, letra maiúscula. letra minúscula e caracter especial.",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        //VERIFICAR SE É UM EMAIL VÁLIDO
        if(!Patterns.EMAIL_ADDRESS.matcher(usuario.getEmailUsuario()).matches()){
            Toast.makeText(context, "Digite um email válido.",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        boolean result = usuarioDAO.inserir(usuario);
        return result;

    }

    public boolean alterar(Usuario usuario){
        //IMPLEMENTAR AS REGRAS DE NEGOCIO
        if(usuario.getNomeUsuario().isEmpty()){
            Toast.makeText(context, "O campo nome não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(usuario.getEmailUsuario().isEmpty()){
            Toast.makeText(context, "O campo email não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(usuario.getSenhaUsuario().isEmpty()){
            Toast.makeText(context, "O campo senha não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        String regraSenha = "(?=.*[0-8])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$)[^@\\.;\\(\\)\\_!?&\\-\\+\\^\\´âêôáéíóúÂÊÔÁÉÍÓÚ]+$";
        if(!usuario.getSenhaUsuario().matches(regraSenha)){
            Toast.makeText(context, "O campo senha deve ter no mínimo 8 caracteres, letra maiúscula. letra minúscula e caracter especial.",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        //VERIFICAR SE É UM EMAIL VÁLIDO
        if(!Patterns.EMAIL_ADDRESS.matcher(usuario.getEmailUsuario()).matches()){
            Toast.makeText(context, "Digite um email válido.",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        boolean result = usuarioDAO.alterar(usuario);
        return result;
    }

    public boolean excluir(Usuario usuario){
        return usuarioDAO.deletar(usuario);
    }

    public Usuario login (String email, String senha){
        //VERIFICAR SE É UM EMAIL VÁLIDO
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context, "Digite um email válido.",
                    Toast.LENGTH_LONG).show();
            return null;
        }
        if(email.isEmpty()){
            Toast.makeText(context, "O campo email não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return null;
        }
        if(senha.isEmpty()){
            Toast.makeText(context, "O campo senha não pode ficar vazio",
                    Toast.LENGTH_LONG).show();
            return null;
        }

        return usuarioDAO.login(email, senha);

    }







}
