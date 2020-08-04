package usuario.usuario;

import exceptions.*;
import organizacion.Organizacion;
import persistencia.Repositorio;
import usuario.password.passwordValidador.PasswordValidator;

import java.io.IOException;

public class UsuarioFactory {
    private static Repositorio<Usuario> repositorio;

    public static Usuario crearUsuario(String username, String password, Organizacion organizacion, Rol rol) throws IOException, PassawordException, UserException {
        Usuario usuario;
        UserValidator.getInstancia().checkUsername(username);
        PasswordValidator.validatePassword(password);
        usuario = new Usuario(username,password,organizacion,rol);
        repositorio.agregar(usuario);
        return usuario;
    }

    public static void setRepositorio(Repositorio<Usuario> repositorio) {
        UsuarioFactory.repositorio = repositorio;
    }

    public static Repositorio<Usuario> getRepositorio() {
        return repositorio;
    }
}
