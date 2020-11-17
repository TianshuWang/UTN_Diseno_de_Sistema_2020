package controller;

import direccion_moneda.DireccionPostal;
import direccion_moneda.mercadolibre_api.model.Ciudad;
import direccion_moneda.mercadolibre_api.model.Pais;
import direccion_moneda.mercadolibre_api.model.Provincia;
import egreso.Proveedor;
import exceptions.BusinessError;
import exceptions.TransactionException;
import organizacion.Organizacion;
import organizacion.OrganizacionBase;
import organizacion.organizacionJuridica.ExcepcionDeCreacionDeOrgnizacionBuilder;
import organizacion.organizacionJuridica.OrganizacionJuridica;
import organizacion.organizacionJuridica.OrganizacionJuridicaBuilder;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.password.passwordValidador.PasswordValidator;
import usuario.usuario.Usuario;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProveedorController {
    private Repositorio<Usuario> repoUsuario;
    private Repositorio<Organizacion> repoOrganizacion;
    private Repositorio<Pais> repoPais;
    private Repositorio<Provincia> repoProvincia;
    private Repositorio<Ciudad> repoCiudad;
    private Repositorio<Proveedor> repoPoveedor;
    private Repositorio<DireccionPostal> repoDireccion;

    public ProveedorController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
        this.repoPais = new FactoryRepositorio().get(Pais.class);
        this.repoProvincia = new FactoryRepositorio().get(Provincia.class);
        this.repoCiudad = new FactoryRepositorio().get(Ciudad.class);
        this.repoPoveedor = new FactoryRepositorio().get(Proveedor.class);
        this.repoDireccion = new FactoryRepositorio().get(DireccionPostal.class);
    }


    public ModelAndView viewCrearProveedor(Request request, Response response) throws TransactionException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        List<Pais> paises = repoPais.buscarTodos();

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario", usuario);
        parametros.put("rol", rol);
        parametros.put("isAdm",usuario.getisAdm());
        parametros.put("paises",paises);
        EntityManagerHelper.closeEntityManager();
        return new ModelAndView(parametros, "proveedor.hbs");
    }

    public String crearNuevoProveedor(Request request, Response response) throws TransactionException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        String nombre  = request.queryParams("nombre");
        String cuit = request.queryParams("cuit");
        String calle = request.queryParams("password1");
        Integer altura = Integer.parseInt(request.queryParams("altura"));
        String pais = request.queryParams("pais");
        String provincia = request.queryParams("provincia");
        String ciudad = request.queryParams("ciudad");

        List<Proveedor> proveedors = repoPoveedor.buscarTodos();
        if(proveedors.stream().filter(p->cuit.equals(p.getCuit())).findAny().orElse(null) != null)
            throw new TransactionException(BusinessError.PROVEEDOR_EXCEPTION);
        DireccionPostal direccionPostal = new DireccionPostal.Builder()
                .agregarCalle(calle)
                .agregarAltura(altura)
                .agregarPais(repoPais.buscar(pais))
                .agregarProvincia(repoProvincia.buscar(provincia))
                .agregarCiudad(repoCiudad.buscar(ciudad))
                .build();
        repoDireccion.agregar(direccionPostal);
        Proveedor proveedor = new Proveedor.Builder()
                .agregarNombre(nombre)
                .agregarCuit(cuit)
                .agregarDireccionPostal(direccionPostal)
                .build();

        repoPoveedor.agregar(proveedor);
        EntityManagerHelper.closeEntityManager();
        return "Proveedor: " + nombre + " fue creado correctamente";
    }

}
