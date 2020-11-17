package controller;

import direccion_moneda.mercadolibre_api.model.Moneda;
import direccion_moneda.mercadolibre_api.services.ImportadorMercadoLibreApi;
import egreso.Egreso;
import egreso.ItemEgreso;
import egreso.ProductoServicio;
import egreso.Proveedor;
import exceptions.BusinessError;
import exceptions.TransactionException;
import organizacion.categoria.Categoria;
import organizacion.criterio.CategoriaClasificacion;
import organizacion.criterio.CriterioClasificacion;
import presupuesto.ItemPresupuesto;
import presupuesto.Presupuesto;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.usuario.Usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PresupuestoController {

    private Repositorio<Usuario> repoUsuario;
    private Repositorio<Moneda> repoMoneda;
    private Repositorio<Egreso> repoEgreso;
    private Repositorio<Presupuesto> repoPresupuesto;
    private Repositorio<Proveedor> repoProveedor;
    private Repositorio<CategoriaClasificacion> repoCategorias;
    private Repositorio<ProductoServicio> repoProductoServicio;
    private Repositorio<ItemPresupuesto> repoItemsPresupuesto;

    public PresupuestoController() {
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repoMoneda = FactoryRepositorio.get(Moneda.class);
        this.repoEgreso = FactoryRepositorio.get(Egreso.class);
        this.repoPresupuesto = FactoryRepositorio.get(Presupuesto.class);
        this.repoProveedor = FactoryRepositorio.get(Proveedor.class);
        this.repoCategorias = FactoryRepositorio.get(CategoriaClasificacion.class);
        this.repoProductoServicio = FactoryRepositorio.get(ProductoServicio.class);
        this.repoItemsPresupuesto = FactoryRepositorio.get(ItemPresupuesto.class);
    }

    public ModelAndView viewPresupuestos(Request request, Response response) throws TransactionException {

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        List<Moneda> monedas = repoMoneda.buscarTodos();

        List<Egreso> egresosUsuario = repoEgreso.buscarTodos().stream()
                .filter(e ->e.getUsuario() !=null && e.getUsuario().getUsername().equalsIgnoreCase(username)).collect(Collectors.toList());

        List<Proveedor> proveedores = repoProveedor.buscarTodos();

        List<Presupuesto> presupuestos = repoPresupuesto.buscarTodos().stream()
                .filter(c -> c.getEgreso().getUsuario() != null && c.getEgreso().getUsuario().getOrganizacion().getId() == usuario.getOrganizacion().getId())
                .collect(Collectors.toList());

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario", usuario);
        parametros.put("rol", rol);
        parametros.put("monedas", monedas);
        parametros.put("egresos", egresosUsuario);
        parametros.put("proveedores",proveedores);
        parametros.put("presupuestos", presupuestos);
        parametros.put("isAdm",usuario.getisAdm());
        EntityManagerHelper.closeEntityManager();
        return new ModelAndView(parametros, "presupuestos.hbs");
    }

    public ModelAndView viewAgregarPresupuesto(Request request, Response response) throws TransactionException {

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        List<Moneda> monedas = repoMoneda.buscarTodos();

        List<Egreso> egresos = repoEgreso.buscarTodos().stream()
                .filter(e->e.getUsuario() !=null && e.getUsuario().getOrganizacion().getId() == usuario.getOrganizacion().getId() &&
                        e.getRequerirPresupuesto() == true &&
                        e.getValidado() == false)
                .collect(Collectors.toList());
        List<Proveedor>proveedores = repoProveedor.buscarTodos();

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario", usuario);
        parametros.put("rol", rol);
        parametros.put("monedas", monedas);
        parametros.put("egresos", egresos);
        parametros.put("proveedores",proveedores);
        parametros.put("isAdm",usuario.getisAdm());
        EntityManagerHelper.closeEntityManager();
        return new ModelAndView(parametros, "presupuesto.hbs");
    }

    public String agregarPresupuesto(Request request, Response response) throws TransactionException, IOException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        int egresoAsociado = Integer.parseInt(request.queryParams("egreso"));
        Egreso egreso = repoEgreso.buscar(egresoAsociado);
        Moneda m = egreso.getMoneda();
        LocalDate fechaPresupuesto = LocalDate.parse(request.queryParams("fecha-presupuesto"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        int idProveedor = Integer.parseInt(request.queryParams("proveedor"));
        Proveedor proveedor = repoProveedor.buscar(idProveedor);

        String[] nombres = request.queryParamsValues("nombre");
        String[] descripciones = request.queryParamsValues("descripcion");
        String[] tipos = request.queryParamsValues("tipo-producto");
        Double[] preciosUnitarios = Arrays.stream(request.queryParamsValues("precio-unitario")).map(Double::valueOf).toArray(Double[]::new);
        Integer[] cantidades = Arrays.stream(request.queryParamsValues("cantidad")).map(Integer::valueOf).toArray(Integer[]::new);

        int cantidadAAgregar = nombres.length;
 
        List<ItemPresupuesto> itemsPresupuesto = new ArrayList<>();
        for(int i = 0 ; i < cantidadAAgregar ; i ++) {
            ProductoServicio productoServicio = new ProductoServicio(nombres[i],descripciones[i],tipos[i]);
            repoProductoServicio.agregar(productoServicio);

            ItemPresupuesto item = new ItemPresupuesto
                    .Builder()
                    .addProductoServicio(productoServicio)
                    .addCantidad(cantidades[i])
                    .addPrecioUnitatio(preciosUnitarios[i])
                    .addMoneda(m)
                    .build();

            repoItemsPresupuesto.agregar(item);
            itemsPresupuesto.add(item);
        }

        Presupuesto nuevoPresupuesto = new Presupuesto
                .Builder()
                .agregarFechaPresupuesto(fechaPresupuesto)
                .agregarItemsPresupuesto(itemsPresupuesto)
                .agregarMoneda(m)
                .agregarEgreso(egreso)
                .agregarProveedor(proveedor)
                .build();

        repoPresupuesto.agregar(nuevoPresupuesto);

        egreso.addPresupuestosRequeridos(nuevoPresupuesto);
        egreso.indicarPresupuestoSeleccionado();
        repoEgreso.modificar(egreso);
        EntityManagerHelper.closeEntityManager();
        return "Presupuesto cargado exitosamente";
    }

    public Response asociarPresupuesto(Request request, Response response) throws TransactionException, IOException {

        String username = request.session().attribute("currentUser");
        if(username == null) throw new TransactionException(BusinessError.SESSION_EXCEPTION);

        String[] categorias = request.queryParamsValues("categorias");
        int idPresupuesto = Integer.parseInt(request.queryParams("id-presupuesto"));

        Integer[] ids = Arrays.stream(categorias).map(Integer::valueOf).toArray(Integer[]::new);

        Presupuesto presupuesto = repoPresupuesto.buscar(idPresupuesto);

        for(int i = 0 ; i < ids.length ; i ++) {
            CategoriaClasificacion categoria = repoCategorias.buscar(ids[i]);
            if(!presupuesto.getCategoriasList().stream().anyMatch(c->c.getDescripcion().equalsIgnoreCase(categoria.getDescripcion()))){
                presupuesto.getCategoriasList().add(categoria);
            }
        }

        repoPresupuesto.modificar(presupuesto);
        EntityManagerHelper.closeEntityManager();
        response.redirect("/presupuestos");
        return response;
    }
}