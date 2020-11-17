package server;

import com.github.jknack.handlebars.Helper;
import controller.*;
import exceptions.TransactionException;
import ingreso.Ingreso;
import spark.ResponseTransformer;
import spark.Route;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;
import spark.utils.IsEqualsHelper;
import spark.utils.IsHelper;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .withHelper("isEquals", IsEqualsHelper.isEquals)
                .withHelper("is", IsHelper.is)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() {

        LoginController loginController = new LoginController();
        MenuController menuController = new MenuController();
        BandejaController bandejaController = new BandejaController();
        UsuarioController usuarioController = new UsuarioController();
        AsociacionIngresosEgresosController asociacionIngresosEgresosController = new AsociacionIngresosEgresosController();
        IngresoController ingresoController = new IngresoController();
        EgresoController egresoController = new EgresoController();
        PresupuestoController presupuestoController = new PresupuestoController();
        CriterioCategoriaController criterioCategoriaController = new CriterioCategoriaController();
        OrganizacionController organizacionController = new OrganizacionController();
        DireccionController direccionController = new DireccionController();
        ProveedorController proveedorController = new ProveedorController();

        Spark.get("/login", loginController::viewLogin, Router.engine);

        Spark.post("/login", loginController::handleLogin);

        Spark.get("/logout", loginController::handleLogout);

        Spark.get("/menu", menuController::ingresarMenu, Router.engine);

        Spark.get("/egresos", egresoController::viewEgresos, Router.engine);

        Spark.get("/descarga/:id", egresoController::descargarArchivo);

        Spark.post("/egresos/categorias", egresoController::agregarCategorias);

        Spark.post("/archivo",egresoController::agregarArchivo);

        Spark.post("/link",egresoController::agregarLink);

        Spark.get("/egreso", egresoController::viewAgregarEgreso, Router.engine);

        Spark.post("/egreso", egresoController::agregarEgreso);

        Spark.get("/presupuestos", presupuestoController::viewPresupuestos, Router.engine);

        Spark.get("/presupuesto", presupuestoController::viewAgregarPresupuesto, Router.engine);

        Spark.post("/presupuestos/categorias", presupuestoController::asociarPresupuesto);

        Spark.post("/presupuesto", presupuestoController::agregarPresupuesto);

        Spark.get("/ingresos", ingresoController::viewIngresos, Router.engine);

        Spark.post("/ingresos", ingresoController::viewIngresos, Router.engine);

        Spark.get("/ingreso", ingresoController::viewAgregarIngreso, Router.engine);

        Spark.post("/ingreso", ingresoController::agregarIngreso);

        Spark.get("/mensajes", bandejaController::ingresarBandeja,Router.engine);

        Spark.put("/mensajes/:id", bandejaController::leerMensaje);

        Spark.get("/asociar", asociacionIngresosEgresosController::viewAsociarIngresosEgresos,Router.engine);

        Spark.post("/asociar", asociacionIngresosEgresosController::asociarIngresosEgresos);

        Spark.get("/usuarios", usuarioController::viewUsuarios, Router.engine);

        Spark.get("/usuario", usuarioController::viewCrearUsuario, Router.engine);

        Spark.post("/usuario", usuarioController::crearNuevoUsuario);

        Spark.get("/organizacion", organizacionController::viewCrearOrganizacion, Router.engine);

        Spark.post("/organizacion", organizacionController::crearNuevaOrganizacion);

        Spark.post("/provincias/:id", direccionController::getProvincias);

        Spark.post("/ciudades/:id", direccionController::getCiudades);

        Spark.get("/proveedor", proveedorController::viewCrearProveedor, Router.engine);

        Spark.post("/proveedor", proveedorController::crearNuevoProveedor);

        Spark.get("/criterios", criterioCategoriaController::viewCriterioCategoria, Router.engine);

        Spark.get("/criterio", criterioCategoriaController::viewCrearCriterioCategoria, Router.engine);

        Spark.post("/criterio", criterioCategoriaController::crearCriterioCategoria);


        Spark.exception(TransactionException.class, (exception, request, response) -> {
            response.status(exception.getErrorCode());
            response.body(exception.getErrorMsg());
        });
    }
}
