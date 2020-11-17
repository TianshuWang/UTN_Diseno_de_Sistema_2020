package controller;

import APIAsociacion.Operacion;
import APIAsociacion.services.ServicioApiAsociacion;
import egreso.Egreso;
import exceptions.BusinessError;
import exceptions.TransactionException;
import ingreso.Ingreso;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import organizacion.Organizacion;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.password.Password;
import usuario.password.passwordValidador.PasswordValidator;
import usuario.usuario.UserValidator;
import usuario.usuario.Usuario;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsociacionIngresosEgresosController {
    private Repositorio<Usuario> repoUsuario;
    private Repositorio<Egreso> repoEgreso;
    private Repositorio<Ingreso> repoIngreso;

    public AsociacionIngresosEgresosController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repoEgreso = FactoryRepositorio.get(Egreso.class);
        this.repoIngreso = FactoryRepositorio.get(Ingreso.class);
    }

    public ModelAndView viewAsociarIngresosEgresos(Request request, Response response) throws TransactionException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario",usuario);
        parametros.put("rol",rol);
        parametros.put("isAdm",usuario.getisAdm());
        EntityManagerHelper.closeEntityManager();

        return new ModelAndView(parametros, "asociarIngresosEgresos.hbs");
    }

    public String asociarIngresosEgresos(Request request, Response response) throws IOException, TransactionException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);

        String rangoFecha = request.queryParams("rangoFecha");
        String criterioOrden = request.queryParams("criterioOrden");
        String vincularPor = request.queryParams("vincularPor");

        Integer rangoF = "".equals(rangoFecha)?30:Integer.valueOf(rangoFecha);
        Map<String, List<Operacion>> resAsociacion = ServicioApiAsociacion.instancia().envioJsonPost(usuario,rangoF,vincularPor,criterioOrden);
        EntityManagerHelper.closeEntityManager();

        return "Asociacion Fue Realizada Correctamente";
    }

}
