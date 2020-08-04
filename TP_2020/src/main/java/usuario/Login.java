package usuario;

import persistencia.Repositorio;
import usuario.password.passwordValidador.PasswordValidator;
import exceptions.*;
import organizacion.Organizacion;
import usuario.usuario.Rol;
import usuario.usuario.Usuario;
import usuario.usuario.UserValidator;
import usuario.usuario.UsuarioFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Login {

    static void crearUsuario(String username, String password, Organizacion organizacion, Rol rol) throws IOException,UserException,PassawordException {
        UserValidator.getInstancia().checkUsername(username);
        new PasswordValidator().validatePassword(password);
        Usuario usuario = UsuarioFactory.crearUsuario(username,password,organizacion,rol);
    }

    static Usuario ingresarUsuario(String username, String password) throws FileNotFoundException, UserException, PassawordException, PasswordExpired {
        UserValidator.getInstancia().checkCredential(username,password);
        Usuario user = UsuarioFactory.getRepositorio().buscar(username);
        return user;
    }
}
