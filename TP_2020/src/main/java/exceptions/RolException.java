package exceptions;

import usuario.usuario.Usuario;

public class RolException extends Exception{
    public RolException(String m){
        super(m);
    }

    public RolException(Usuario user){
        super("No Es Revisor Del Usuario:"+user.getUsername());
    }
}
