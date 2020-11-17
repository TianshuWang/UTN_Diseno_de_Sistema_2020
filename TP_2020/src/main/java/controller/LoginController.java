package controller;

import exceptions.TransactionException;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import usuario.usuario.UserValidator;
import usuario.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController {
    private Repositorio<Usuario> repoUsuario;

    public LoginController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
    }

    public ModelAndView viewLogin(Request request, Response response){

        return new ModelAndView(null,"login.hbs");
    }

    public Response handleLogin(Request request, Response response) throws TransactionException {
        String username = request.queryParams("username");
        String password = request.queryParams("password");
        UserValidator.checkCredential(username,password);

        Session session = request.session(true);
        session.attribute("currentUser", username);
        EntityManagerHelper.closeEntityManager();
        return response;
    }

    public Response handleLogout(Request request, Response response) {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect("/login");

        return response;
    }
}
