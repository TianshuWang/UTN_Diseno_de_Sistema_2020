package controller;

import exceptions.BusinessError;
import exceptions.TransactionException;
import presupuesto.scheduler.Scheduler;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.usuario.Usuario;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class MenuController {
    private Repositorio<Usuario> repoUsuario;

    public MenuController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
    }

    public ModelAndView ingresarMenu(Request request, Response response) throws FileNotFoundException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");
        Usuario usuario = repoUsuario.buscar(username);


        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario",usuario);
        parametros.put("rol",rol);
        parametros.put("isAdm",usuario.getisAdm());

        request.session().attribute("usuario",usuario);
        EntityManagerHelper.closeEntityManager();
        return usuario.getisAdm()==true ? new ModelAndView(parametros,"administrador.hbs")
                :new ModelAndView(parametros,"comun.hbs")  ;
    }
}
