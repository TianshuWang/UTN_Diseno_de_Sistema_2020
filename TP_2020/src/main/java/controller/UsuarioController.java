package controller;

import direccion_moneda.mercadolibre_api.model.Moneda;
import egreso.Egreso;
import exceptions.BusinessError;
import exceptions.TransactionException;
import organizacion.Organizacion;
import organizacion.categoria.CalculoCategoriaAFIP;
import organizacion.organizacionJuridica.Empresa;
import organizacion.organizacionJuridica.TipoOrganizacionJuridica;
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

import javax.persistence.Embedded;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsuarioController {
    private PasswordValidator validadorClave;
    private Repositorio<Usuario> repoUsuario;
    private Repositorio<Organizacion> repoOrganizacion;
    private Repositorio<Password> repoPass;
    private Repositorio<TipoOrganizacionJuridica> repoTipo;

    public UsuarioController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
        this.validadorClave = new PasswordValidator();
        this.repoPass = FactoryRepositorio.get(Password.class);
        this.repoTipo = FactoryRepositorio.get(TipoOrganizacionJuridica.class);
    }

    public ModelAndView viewUsuarios(Request request, Response response) throws TransactionException, FileNotFoundException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";
        List<Usuario> usuarios = repoUsuario.buscarTodos();
        usuarios.remove(usuario);

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario", usuario);
        parametros.put("rol", rol);
        parametros.put("usuarios", usuarios);
        parametros.put("isAdm",usuario.getisAdm());
        EntityManagerHelper.closeEntityManager();
        return new ModelAndView(parametros, "usuarios.hbs");
    }

    public ModelAndView viewCrearUsuario(Request request, Response response) throws TransactionException {

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        List<Organizacion> organizaciones = repoOrganizacion.buscarTodos();

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario", usuario);
        parametros.put("rol", rol);
        parametros.put("organizaciones", organizaciones);
        parametros.put("isAdm",usuario.getisAdm());
        EntityManagerHelper.closeEntityManager();
        return new ModelAndView(parametros, "usuario.hbs");
    }

    public String crearNuevoUsuario(Request request, Response response) throws TransactionException, FileNotFoundException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        String nombre = request.queryParams("nombre");
        String apellido = request.queryParams("apellido");
        String nuevoUsuario = request.queryParams("username");
        String password1 = request.queryParams("password");
        int idOrganizacion = Integer.parseInt(request.queryParams("organizacion"));
        String administrador = request.queryParams("administrador");
        String revisor = request.queryParams("revisor");
        UserValidator.checkUsername(nuevoUsuario);

        validadorClave.validatePassword(password1);

        Organizacion organizacion = repoOrganizacion.buscar(idOrganizacion);

        boolean esAdmin = "on".equals(administrador)?true:false;
        boolean esRevisor = "on".equals(revisor)?true:false;

        Password pass = new Password(password1);
        repoPass.agregar(pass);
        Usuario nuevoUser = new Usuario.Builder().
                agregarUsername(nuevoUsuario).
                agregarPassword(pass).
                agregarIsAdm(esAdmin).
                agregarIsRevisor(esRevisor).
                agregarOrganizacion(organizacion).
                agregarNombre(nombre).
                agregarApellido(apellido).
                build();

        repoUsuario.agregar(nuevoUser);
        EntityManagerHelper.closeEntityManager();
        return "Usuario: " + nuevoUsuario + " fue creado correctamente";
    }
}
