package controller;

import direccion_moneda.mercadolibre_api.model.Moneda;
import egreso.Egreso;
import entityPersistente.EntidadPersistente;
import exceptions.BusinessError;
import exceptions.TransactionException;
import ingreso.Ingreso;
import organizacion.criterio.CategoriaClasificacion;
import presupuesto.Presupuesto;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.usuario.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IngresoController {
    private Repositorio<Usuario> repoUsuario;
    private Repositorio<Ingreso> repoIngreso;
    private Repositorio<Moneda> repoMoneda;
    private Repositorio<Egreso> repoEgreso;

    public IngresoController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repoIngreso = FactoryRepositorio.get(Ingreso.class);
        this.repoMoneda = FactoryRepositorio.get(Moneda.class);
        this.repoEgreso = FactoryRepositorio.get(Egreso.class);
    }

    public ModelAndView viewIngresos(Request request, Response response) throws TransactionException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        List<Ingreso> ingresos = repoIngreso.buscarTodos().stream()
                .filter(i->i.getUsuario().getOrganizacion().getId() == usuario.getOrganizacion().getId()).collect(Collectors.toList());

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario",usuario);
        parametros.put("rol",rol);
        parametros.put("ingresos",ingresos);
        parametros.put("isAdm",usuario.getisAdm());
        EntityManagerHelper.closeEntityManager();
        return new ModelAndView(parametros, "ingresos.hbs");
    }

    public ModelAndView viewAgregarIngreso(Request request, Response response) throws TransactionException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        List<Moneda> monedas = repoMoneda.buscarTodos();

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario",usuario);
        parametros.put("rol",rol);
        parametros.put("monedas",monedas);
        parametros.put("isAdm",usuario.getisAdm());
        EntityManagerHelper.closeEntityManager();
        return new ModelAndView(parametros, "ingreso.hbs");
    }

    public String agregarIngreso(Request request, Response response) throws TransactionException {

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        String fechaOperacion = request.queryParams("fecha-operacion");
        String montoTotal = request.queryParams("monto-total");
        String moneda = request.queryParams("moneda");
        String descripcion = request.queryParams("descripcion");

        LocalDate fechaOp = LocalDate.parse(fechaOperacion, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Double monto = Double.valueOf(montoTotal);
        Moneda m = repoMoneda.buscar(moneda);
        Usuario user = repoUsuario.buscar(username);

        Ingreso ingreso = new Ingreso.Builder()
                .agregarUsuario(user)
                .agregarDescripcion(descripcion)
                .agregarMontoTotal(monto)
                .agregarMoneda(m)
                .agregarFechaDeOperacion(fechaOp)
                .build();
        repoIngreso.agregar(ingreso);
        EntityManagerHelper.closeEntityManager();
        return "Ingreso Fue Cargado Correctamente";
    }

    public List<Egreso> getEgresosAsociados(Request request, Response response) throws TransactionException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Integer id = Integer.parseInt(request.queryParams("id"));
        List<Egreso> egresoList = repoIngreso.buscar(id).getEgresosAsociados();
        EntityManagerHelper.closeEntityManager();
        return egresoList;
    }
}