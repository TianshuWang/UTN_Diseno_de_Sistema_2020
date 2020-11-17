package usuario.usuario;

import repositorio.DAOHibernate;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import usuario.password.PasswordLifeTimeValidator;
import exceptions.*;

import java.io.FileNotFoundException;

public class UserValidator {
    private static Repositorio<Usuario> repositorioUsuario;

    static {
        repositorioUsuario = new Repositorio(new DAOHibernate<>(Usuario.class));
    }

    public static void checkUsername(String username) throws TransactionException {
        if(repositorioUsuario.buscar(username) != null){
            throw new TransactionException(BusinessError.USER_EXISTED);
        }
    }

    public static void checkCredential(String username, String password) throws TransactionException {
        Usuario aux = repositorioUsuario.buscar(username);
        if(aux == null){
            throw new TransactionException(BusinessError.USER_NOT_MATCH);
        }

        checkPassword(aux,password);
    }

    public static void checkPassword(Usuario user, String password) throws TransactionException {
        if(!user.getPassword().getContent().equals(password)){
            throw new TransactionException(BusinessError.PASSWORD_NOT_MATCH);
        }
        new PasswordLifeTimeValidator().checkPasswordLastUpdate(user.getPassword());
    }
}
