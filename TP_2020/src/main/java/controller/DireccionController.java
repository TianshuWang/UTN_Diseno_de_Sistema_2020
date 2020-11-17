package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import direccion_moneda.mercadolibre_api.model.Ciudad;
import direccion_moneda.mercadolibre_api.model.Pais;
import direccion_moneda.mercadolibre_api.model.Provincia;
import exceptions.BusinessError;
import exceptions.TransactionException;
import organizacion.Organizacion;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.password.Password;
import usuario.password.passwordValidador.PasswordValidator;
import usuario.usuario.UserValidator;
import usuario.usuario.Usuario;

import javax.print.attribute.standard.MediaSize;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DireccionController {
    private PasswordValidator validadorClave;
    private Repositorio<Usuario> repoUsuario;
    private Repositorio<Organizacion> repoOrganizacion;
    private Repositorio<Pais> repoPais;
    private Repositorio<Provincia> repoProvincia;
    private Repositorio<Ciudad> repoCiudad;

    public DireccionController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
        this.validadorClave = new PasswordValidator();
        this.repoPais = new FactoryRepositorio().get(Pais.class);
        this.repoProvincia = new FactoryRepositorio().get(Provincia.class);
        this.repoCiudad = new FactoryRepositorio().get(Ciudad.class);
    }

    public String getProvincias(Request request, Response response) throws TransactionException, FileNotFoundException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        String id = request.params("id");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class,new LocalDateAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        String jsonDatos = gson.toJson(repoPais.buscar(id).getProvincias());
        EntityManagerHelper.closeEntityManager();
        return jsonDatos;
    }

    public String getCiudades(Request request, Response response) throws TransactionException, FileNotFoundException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        String id = request.params("id");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class,new LocalDateAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        String jsonDatos = gson.toJson(repoProvincia.buscar(id).getCiudades());
        EntityManagerHelper.closeEntityManager();
        return jsonDatos;
    }
}
