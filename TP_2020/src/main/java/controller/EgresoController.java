package controller;

import direccion_moneda.mercadolibre_api.model.Moneda;
import egreso.*;
import egreso.documento.Comprobante;
import egreso.documento.Documento;
import egreso.documento.Link;
import exceptions.BusinessError;
import organizacion.criterio.CategoriaClasificacion;
import organizacion.criterio.CriterioClasificacion;
import exceptions.TransactionException;
import organizacion.Organizacion;
import presupuesto.criterioSeleccion.CriterioMenorCosto;
import presupuesto.criterioSeleccion.CriterioSeleccion;
import presupuesto.scheduler.Scheduler;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import usuario.usuario.Usuario;

import javax.print.Doc;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class EgresoController {

    private Repositorio<Usuario> repoUsuario;
    private Repositorio<Moneda> repoMoneda;
    private Repositorio<Proveedor> repoProveedor;
    private Repositorio<Egreso> repoEgreso;
    private Repositorio<Organizacion> repoOrganizacion;
    private Repositorio<CriterioClasificacion> repoCriterios;
    private Repositorio<CategoriaClasificacion> repoCategorias;
    private Repositorio<Documento> repoDocu;
    private Repositorio<MedioDePago> repoMedioDePago;
    private Repositorio<ItemEgreso> repoItemEgreso;

    public EgresoController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repoMoneda = FactoryRepositorio.get(Moneda.class);
        this.repoProveedor = FactoryRepositorio.get(Proveedor.class);
        this.repoEgreso = FactoryRepositorio.get(Egreso.class);
        this.repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
        this.repoCriterios = FactoryRepositorio.get(CriterioClasificacion.class);
        this.repoCategorias = FactoryRepositorio.get(CategoriaClasificacion.class);
        this.repoDocu = FactoryRepositorio.get(Documento.class);
        this.repoMedioDePago = FactoryRepositorio.get(MedioDePago.class);
        this.repoItemEgreso = FactoryRepositorio.get(ItemEgreso.class);
    }

    public ModelAndView viewEgresos(Request request, Response response) throws TransactionException, FileNotFoundException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        String usuarioOrganizacion = usuario.getOrganizacion().getNombre();

        List<Moneda> monedas = repoMoneda.buscarTodos();
        List<Proveedor> proveedores = repoProveedor.buscarTodos();

        List<Egreso> egresosOrganizacion = repoEgreso.buscarTodos().stream()
                .filter(c -> c.getUsuario() != null && c.getUsuario().getOrganizacion().getId() == usuario.getOrganizacion().getId())
                .collect(Collectors.toList());

        List<CategoriaClasificacion> categorias = repoCategorias.buscarTodos().stream()
                .filter(c -> c.getCriterioClasificacion().getOrganizacion().getId() == usuario.getOrganizacion().getId()).distinct()
                .collect(Collectors.toList());

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario",usuario);
        parametros.put("rol",rol);
        parametros.put("monedas",monedas);
        parametros.put("proveedores", proveedores);
        parametros.put("egresosOrganizacion", egresosOrganizacion);
        parametros.put("isAdm",usuario.getisAdm());
        parametros.put("categorias", categorias);

        new Scheduler().run(usuario.getOrganizacion());

        EntityManagerHelper.closeEntityManager();
        return new ModelAndView(parametros, "egresos.hbs");
    }

    public ModelAndView viewAgregarEgreso(Request request, Response response) throws TransactionException {

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";

        List<Moneda> monedas = repoMoneda.buscarTodos();
        List<Proveedor> proveedores = repoProveedor.buscarTodos();

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("usuario",usuario);
        parametros.put("rol",rol);
        parametros.put("monedas",monedas);
        parametros.put("proveedores", proveedores);
        parametros.put("isAdm",usuario.getisAdm());
        EntityManagerHelper.closeEntityManager();
        return new ModelAndView(parametros, "egreso.hbs");
    }

    public String agregarEgreso(Request request, Response response) throws TransactionException, IOException, ServletException {

        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");
        Usuario user = repoUsuario.buscar(username);

        List<ItemEgreso> listaItems = new ArrayList<>();

        int idProveedor = Integer.parseInt(request.queryParams("proveedor"));
        Proveedor proveedor = repoProveedor.buscar(idProveedor);
        LocalDate fechaDeOperacion = LocalDate.parse(request.queryParams("fecha-operacion"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Moneda moneda = repoMoneda.buscar(request.queryParams("moneda"));

        String nombreTarjeta;
        String numeroTarjeta;
        String tipoDePago = request.queryParams("tipo-pago");
        if(!tipoDePago.equalsIgnoreCase("efectivo")) {
            nombreTarjeta = request.queryParams("nombre-tarjeta");
            numeroTarjeta = request.queryParams("numero-tarjeta");
        } else {
            nombreTarjeta = null;
            numeroTarjeta = null;
        }

        String items = request.queryParams("items");

        String[] elementosItems = items.split(",");
        for(int i = 0 ; i < elementosItems.length ; i += 5) {

            ProductoServicio productoServicio = new ProductoServicio(elementosItems[i], elementosItems[i + 1], elementosItems[i + 2]);
            repoProveedor.agregar(productoServicio);

            ItemEgreso item = new ItemEgreso
                    .Builder()
                    .addProductoServicio(productoServicio)
                    .addCantidad(Integer.parseInt(elementosItems[i + 4]))
                    .addPrecioUnitatio(Double.parseDouble(elementosItems[i + 3]))
                    .addMoneda(moneda)
                    .build();
            repoItemEgreso.agregar(item);
            listaItems.add(item);
        }

        MedioDePago medioDePago = new MedioDePago(nombreTarjeta, numeroTarjeta, tipoDePago);
        repoMedioDePago.agregar(medioDePago);

        Egreso egresoNuevo = new EgresoBuilder()
                .agregarFechaDeOperacion(fechaDeOperacion)
                .agregarMedioDePago(medioDePago)
                .agregarMoneda(moneda)
                .agregarProveedor(proveedor)
                .agregarUsuario(user)
                .agregarItemsEgreso(listaItems)
                .build();

        egresoNuevo.getMontoTotal();

        boolean reqPresupuesto = "on".equalsIgnoreCase(request.queryParams("requiere-presupuesto"));

        egresoNuevo.setRequerirPresupuesto(reqPresupuesto);
        egresoNuevo.setValidado(false);

        CriterioSeleccion menorCosto = new CriterioMenorCosto(); // Harcodeado
        egresoNuevo.requerirPresupuesto(menorCosto,user); // Harcodeado

        repoEgreso.agregar(egresoNuevo);

        String tipoDocumentacion = request.queryParams("tipo-documentacion");

        if(tipoDocumentacion.equalsIgnoreCase("link")){
            String enlace = request.queryParams("link");
            Link link = new Link();
            link.setLink(enlace);

            Documento documento = new Documento(proveedor, link, fechaDeOperacion);
            repoDocu.agregar(documento);
            egresoNuevo.setDocumento(documento);
        } else if(tipoDocumentacion.equalsIgnoreCase("fisica")){

            // PARTE SUBIDA ARCHIVO

            File archivosDir = new File("archivos");
            Path path_archivos = Paths.get("archivos");
            if(Files.notExists(path_archivos)) {
                archivosDir.mkdir();
            }

            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            Part filePart = request.raw().getPart("archivo");
            Path archivoDestino = Paths.get(path_archivos + File.separator + filePart.getSubmittedFileName());

            try(InputStream input = filePart.getInputStream()) {
                Files.copy(input, archivoDestino, StandardCopyOption.REPLACE_EXISTING);
            }

            // PARTE ARCHIVO

            Comprobante documentoFisico = new Comprobante();
            documentoFisico.setRutaArchivo(archivoDestino.toString());

            Documento fisico = new Documento(proveedor, documentoFisico, fechaDeOperacion);
            fisico.setEgreso(egresoNuevo);
            repoDocu.agregar(fisico);
            egresoNuevo.setDocumento(fisico);

        } else {
            egresoNuevo.setDocumento(null);
        }

        repoEgreso.modificar(egresoNuevo);
        EntityManagerHelper.closeEntityManager();
        return "Egreso creado satisfactoriamente";
    }


    public Response agregarCategorias(Request request, Response response) throws TransactionException {

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        String[] categoriasSeleccionadas = request.queryParamsValues("categorias");
        Integer[] ids = Arrays.stream(categoriasSeleccionadas).map(Integer::valueOf).toArray(Integer[]::new);

        int egresoAsociado = Integer.parseInt(request.queryParams("id_egreso"));

        Egreso egreso = repoEgreso.buscar(egresoAsociado);

        List<CategoriaClasificacion> categoriasAceptadas = new ArrayList<>();
        // Comparamos todas las categorias que tiene el egreso, si alguna se repite entonces mandamos mensaje de error
        List<CategoriaClasificacion> categoriasEgreso = egreso.getCategoriasList();
        for(int i = 0 ; i < ids.length ; i++) {
            CategoriaClasificacion categoriaSeleccionada = repoCategorias.buscar(ids[i]);
            if(!egreso.getCategoriasList().stream().anyMatch(c -> c.getDescripcion().equalsIgnoreCase(categoriaSeleccionada.getDescripcion()))) {
                categoriasAceptadas.add(categoriaSeleccionada);
            }
        }

        egreso.agregarCategoriasClasificacion(categoriasAceptadas);

        repoEgreso.modificar(egreso);
        EntityManagerHelper.closeEntityManager();
        response.redirect("/egresos");
        return response;
    }

    public Response agregarArchivo(Request request, Response response) throws IOException, ServletException {

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        File archivosDir = new File("archivos");
        Path path_archivos = Paths.get("archivos");
        if(Files.notExists(path_archivos)) {
            archivosDir.mkdir();
        }

        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

        Part filePart = request.raw().getPart("archivo");
        Path archivoDestino = Paths.get(path_archivos + File.separator + filePart.getSubmittedFileName());

        try(InputStream input = filePart.getInputStream()) {
            Files.copy(input, archivoDestino, StandardCopyOption.REPLACE_EXISTING);
        }

        int id_egreso = Integer.parseInt(request.queryParams("id_egreso"));

        Egreso egreso = repoEgreso.buscar(id_egreso);

        Comprobante factura = new Comprobante();
        factura.setRutaArchivo(archivoDestino.toString());

        Documento nuevoDocumento = new Documento(egreso.getProveedor(), factura, egreso.getFechaDeOperacion()); // la fecha de emision es la misma que la del egreso?

        repoDocu.agregar(nuevoDocumento);

        nuevoDocumento.setEgreso(egreso);

        egreso.setDocumento(nuevoDocumento);

        repoEgreso.modificar(egreso);
        EntityManagerHelper.closeEntityManager();
        response.redirect("/egresos");
        return response;
    }

    public Response agregarLink(Request request, Response response) throws IOException, ServletException {

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        String linkS = request.queryParams("link");

        int id_egreso = Integer.parseInt(request.queryParams("id_egreso"));
        Egreso egreso = repoEgreso.buscar(id_egreso);

        Link link = new Link();
        link.setLink(linkS);

        Documento nuevoDocumento = new Documento(egreso.getProveedor(), link, egreso.getFechaDeOperacion());

        egreso.setDocumento(nuevoDocumento);
        repoEgreso.modificar(egreso);
        EntityManagerHelper.closeEntityManager();
        response.redirect("/egresos");
        return response;
    }

    public HttpServletResponse descargarArchivo(Request request, Response response) throws TransactionException, IOException, ServletException {

        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");
        Usuario user = repoUsuario.buscar(username);

        int egreso_id = Integer.parseInt(request.params("id"));

        Egreso egreso = repoEgreso.buscar(egreso_id);

        String rutaDocuEgreso = egreso.getDocumento().getTipoDeDocumento().getRutaArchivo();

        File archivo = new File(rutaDocuEgreso);
        //response.raw().setContentType("application/json; charset=UTF-8");
        if(!archivo.exists()) throw new TransactionException(BusinessError.DOCUMENTOS_EXCEPTION);
        response.raw().setHeader("Content-Disposition", "attachment; filename=" + archivo.getName());

        try {
            OutputStream outputStream = response.raw().getOutputStream();
            outputStream.write(Files.readAllBytes(archivo.toPath()));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Spark.halt(470,"Documento no se Encuentra");
        }
        EntityManagerHelper.closeEntityManager();
        return response.raw();
    }
}
