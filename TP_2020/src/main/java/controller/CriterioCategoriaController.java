package controller;

import APIAsociacion.Operacion;
import APIAsociacion.services.ServicioApiAsociacion;
import egreso.Egreso;
import exceptions.BusinessError;
import exceptions.TransactionException;
import ingreso.Ingreso;
import organizacion.criterio.CategoriaClasificacion;
import organizacion.criterio.CriterioClasificacion;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.usuario.Usuario;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CriterioCategoriaController {
    private Repositorio<Usuario> repoUsuario;
    private Repositorio<Egreso> repoEgreso;
    private Repositorio<Ingreso> repoIngreso;
    private Repositorio<CriterioClasificacion> repoCriterio;
    private Repositorio<CategoriaClasificacion> repoCategoria;

    public CriterioCategoriaController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repoEgreso = FactoryRepositorio.get(Egreso.class);
        this.repoIngreso = FactoryRepositorio.get(Ingreso.class);
        this.repoCriterio = FactoryRepositorio.get(CriterioClasificacion.class);
        this.repoCategoria = FactoryRepositorio.get(CategoriaClasificacion.class);
    }

    public ModelAndView viewCriterioCategoria(Request request, Response response) throws TransactionException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";
        List<CriterioClasificacion> criterios = repoCriterio.buscarTodos().stream()
                .filter(c->c.getOrganizacion().getId() == usuario.getOrganizacion().getId()).collect(Collectors.toList());

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario",usuario);
        parametros.put("rol",rol);
        parametros.put("isAdm",usuario.getisAdm());
        parametros.put("criterios",criterios);
        EntityManagerHelper.closeEntityManager();

        return new ModelAndView(parametros, "criterios.hbs");
    }

    public ModelAndView viewCrearCriterioCategoria(Request request, Response response) throws TransactionException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";
        List<CriterioClasificacion> criterios = repoCriterio.buscarTodos().stream()
                .filter(c->c.getOrganizacion().getId() == usuario.getOrganizacion().getId()).collect(Collectors.toList());

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario",usuario);
        parametros.put("rol",rol);
        parametros.put("isAdm",usuario.getisAdm());
        parametros.put("criterios",criterios);
        EntityManagerHelper.closeEntityManager();

        return new ModelAndView(parametros, "criterio.hbs");
    }

    public String crearCriterioCategoria(Request request, Response response){
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);

        String des_criterio = request.queryParams("des_criterio");
        String[] criterioIds = request.queryParamsValues("criterioId");
        String[] des_categorias = request.queryParamsValues("des_categoria");

        CriterioClasificacion criterio = new CriterioClasificacion(des_criterio,usuario.getOrganizacion());

        if(criterioIds != null){
            Integer[] ids = Arrays.stream(criterioIds).map(Integer::valueOf).toArray(Integer[]::new);

            for(int i=0;i<ids.length;i++){
                criterio.agregarCriteriosHijos(repoCriterio.buscar(ids[i]));
            }
        }

        for(int i=0;i<des_categorias.length;i++){
            CategoriaClasificacion c = new CategoriaClasificacion(des_categorias[i],criterio);
            repoCategoria.agregar(c);
        }
        repoCriterio.agregar(criterio);
        EntityManagerHelper.closeEntityManager();

        return "Criterio Fue Creado Correctamente";
    }

}
