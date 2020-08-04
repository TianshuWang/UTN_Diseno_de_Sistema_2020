package usuario.usuario;

import persistencia.Repositorio;
import usuario.password.PasswordLifeTimeValidator;
import exceptions.*;

import java.io.FileNotFoundException;

public class UserValidator {
    private static UserValidator instancia = null;

    private UserValidator(){
    }

    public static UserValidator getInstancia(){
        if(instancia == null){
            instancia = new UserValidator();
        }
        return instancia;
    }

    public void checkUsername(String username) throws UserException {
        if(UsuarioFactory.getRepositorio().existe(username)){
            throw new UserException(username," Already Exist");
        }
    }

    public void checkCredential(String username, String password) throws UserException, FileNotFoundException, PassawordException, PasswordExpired {
        if(!UsuarioFactory.getRepositorio().existe(username)){
            throw new UserException(username," Dose Not Exists");
        }
        Usuario aux = UsuarioFactory.getRepositorio().buscar(username);
        checkPassword(aux,password);
    }

    public void checkPassword(Usuario user, String password) throws FileNotFoundException, PassawordException, PasswordExpired {
        if(!user.getPassword().getContent().equals(password)){
            throw new PassawordException("Password Entered Incorrectly");
        }
        new PasswordLifeTimeValidator().checkPasswordLastUpdate(user.getPassword());
    }
}
