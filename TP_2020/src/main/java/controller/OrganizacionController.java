package controller;

import direccion_moneda.DireccionPostal;
import direccion_moneda.mercadolibre_api.model.Ciudad;
import direccion_moneda.mercadolibre_api.model.Pais;
import direccion_moneda.mercadolibre_api.model.Provincia;
import exceptions.BusinessError;
import exceptions.TransactionException;
import organizacion.Organizacion;
import organizacion.OrganizacionBase;
import organizacion.categoria.Comisionista;
import organizacion.categoria.NoComisionista;
import organizacion.categoria.Sector;
import organizacion.organizacionJuridica.*;
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
import java.util.stream.Collectors;

public class OrganizacionController {
    private Repositorio<Usuario> repoUsuario;
    private Repositorio<Organizacion> repoOrganizacion;
    private Repositorio<Pais> repoPais;
    private Repositorio<Provincia> repoProvincia;
    private Repositorio<Ciudad> repoCiudad;
    private Repositorio<DireccionPostal> repoDireccion;
    private Repositorio<TipoOrganizacionJuridica> repoTipo;

    public OrganizacionController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
        this.repoPais = new FactoryRepositorio().get(Pais.class);
        this.repoProvincia = new FactoryRepositorio().get(Provincia.class);
        this.repoCiudad = new FactoryRepositorio().get(Ciudad.class);
        this.repoDireccion = new FactoryRepositorio().get(DireccionPostal.class);
        this.repoTipo = new FactoryRepositorio().get(TipoOrganizacionJuridica.class);
    }


    public ModelAndView viewCrearOrganizacion(Request request, Response response) throws TransactionException {

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        List<Pais> paises = repoPais.buscarTodos();
        List<Organizacion> organizaciones = repoOrganizacion.buscarTodos().stream()
                .filter(o->!"Base".equalsIgnoreCase(o.getTipo())).collect(Collectors.toList());

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario", usuario);
        parametros.put("rol", rol);
        parametros.put("isAdm",usuario.getisAdm());
        parametros.put("paises",paises);
        parametros.put("organizaciones", organizaciones);
        EntityManagerHelper.closeEntityManager();
        return new ModelAndView(parametros, "organizacion.hbs");
    }

    public String crearNuevaOrganizacion(Request request, Response response) throws IOException, ExcepcionDeCreacionDeOrgnizacionBuilder {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        String nombre  = request.queryParams("nombre");
        String tipoOrg  = request.queryParams("tipo-org");
        String calle = request.queryParams("calle");
        Integer altura = Integer.parseInt(request.queryParams("altura"));
        String pais = request.queryParams("pais");
        String provincia = request.queryParams("provincia");
        String ciudad = request.queryParams("ciudad");
        DireccionPostal direccionPostal = new DireccionPostal.Builder()
                .agregarCalle(calle)
                .agregarAltura(altura)
                .agregarPais(repoPais.buscar(pais))
                .agregarProvincia(repoProvincia.buscar(provincia))
                .agregarCiudad(repoCiudad.buscar(ciudad))
                .build();

        repoDireccion.agregar(direccionPostal);
        if(!tipoOrg.equalsIgnoreCase("Base")){
            String razonSocial = request.queryParams("razonSocial");
            String cuit = request.queryParams("cuit");
            String nroIGJ = request.queryParams("nroIGJ");
            String tipo = request.queryParams("tipo");
            TipoOrganizacionJuridica tipoOJ;
            if("OSC".equalsIgnoreCase(tipo)){
                tipoOJ = new OSC(request.queryParams("mision"));
            }
            else{
                Integer cant = Integer.parseInt(request.queryParams("cantidad"));
                Long venta = Long.parseLong(request.queryParams("venta"));
                String sector = request.queryParams("sector").toLowerCase();
                tipoOJ = new Empresa(cant,venta, sector, new Comisionista());
            }
            repoTipo.agregar(tipoOJ);
            Organizacion org = new OrganizacionJuridicaBuilder()
                    .agregarCuit(cuit)
                    .agregarDireccionPostal(direccionPostal)
                    .agregarNombre(nombre)
                    .agregarRazonSocial(razonSocial)
                    .agregarNroIGJ(nroIGJ)
                    .agregarTipoDeOrganizacionJjuridica(tipoOJ)
                    .build();

            repoOrganizacion.agregar(org);
        }
        else{
            Integer orgId = Integer.parseInt(request.queryParams("juridicaId"));
            OrganizacionJuridica orgJuridica = (OrganizacionJuridica) repoOrganizacion.buscar(orgId);
            Organizacion org = new OrganizacionBase(orgJuridica,nombre);
            repoOrganizacion.agregar(org);
        }
        EntityManagerHelper.closeEntityManager();
        return "Organizacion: " + nombre + " fue creada correctamente";
    }

}
