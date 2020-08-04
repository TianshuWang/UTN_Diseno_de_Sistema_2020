package exceptions;

import usuario.usuario.Usuario;

public class UserException extends Exception{
    public UserException(String username,String m){
        super("User:"+username+m);
    }

    public UserException(Usuario user){
        super("User:"+user.getUsername()+" No Es Usuario De La Misma Organizacion");
    }
}
