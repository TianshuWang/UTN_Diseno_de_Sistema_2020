package tester;
import direccion_moneda.DireccionPostal;
import direccion_moneda.ExcepcionDeCreacionDeDireccion;
import direccion_moneda.mercadolibre_api.model.Ciudad;
import direccion_moneda.mercadolibre_api.model.Moneda;
import direccion_moneda.mercadolibre_api.model.Pais;
import direccion_moneda.mercadolibre_api.model.Provincia;
import direccion_moneda.mercadolibre_api.services.ImportadorMercadoLibreApi;
import egreso.ItemEgreso;
import egreso.MedioDePago;
import egreso.ProductoServicio;
import egreso.Egreso;
import egreso.EgresoBuilder;
import egreso.Proveedor;
import exceptions.*;
import ingreso.Ingreso;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import organizacion.Organizacion;
import organizacion.categoria.Comisionista;
import organizacion.categoria.NoComisionista;
import organizacion.organizacionJuridica.*;
import presupuesto.ItemPresupuesto;
import presupuesto.Presupuesto;
import presupuesto.criterioSeleccion.CriterioMenorCosto;
import presupuesto.criterioSeleccion.CriterioSeleccion;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import spark.ModelAndView;
import usuario.password.Password;
import usuario.password.passwordValidador.PasswordValidator;
import usuario.usuario.*;

import java.io.IOException;
import java.time.LocalDate;

public class Unit_Test {
    Repositorio<Usuario> repoUsuario = FactoryRepositorio.get(Usuario.class);
    Repositorio<Proveedor> repoProveedor = FactoryRepositorio.get(Proveedor.class);
    Repositorio<Egreso> repoEgreso = FactoryRepositorio.get(Egreso.class);
    Repositorio<Presupuesto> repoPresupuesto = FactoryRepositorio.get(Presupuesto.class);
    Repositorio<ProductoServicio> repoProducto = FactoryRepositorio.get(ProductoServicio.class);
    Repositorio<Organizacion> repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
    Repositorio<Ingreso> repoIngreso = FactoryRepositorio.get(Ingreso.class);
    Repositorio<Pais> repoPais = FactoryRepositorio.get(Pais.class);
    Repositorio<Provincia> repoProvincias = FactoryRepositorio.get(Provincia.class);
    Repositorio<Ciudad> repoCiudad = new FactoryRepositorio().get(Ciudad.class);
    Repositorio<Moneda> repoMoneda = new FactoryRepositorio().get(Moneda.class);
    @Test
    public void test() throws IOException,ExcepcionDeCreacionDeDireccion, ExcepcionDeCreacionDeOrgnizacionBuilder, TransactionException {
        //ImportadorMercadoLibreApi.importarPaises();
        TipoOrganizacionJuridica juridicaEmpresa = new Empresa(8,8000000, "Servicios", new Comisionista());
        System.out.println(juridicaEmpresa.getCategoria());
        /*cargarUsuarioAdministrador();
        cargarProveedor();
        cargarEgreso();
        cargarPresupuesto();
        cargarIngreso();*/
    }


    @Test
    public void cargarUsuarioAdministrador() throws IOException,ExcepcionDeCreacionDeOrgnizacionBuilder, TransactionException {


        DireccionPostal direccion = new DireccionPostal.Builder()
                .agregarPais(repoPais.buscar("Argentina"))
                .agregarProvincia(repoProvincias.buscar("Capital Federal"))
                .agregarCiudad(repoCiudad.buscar("Almagro"))
                .agregarCalle("Medrano")
                .agregarAltura(999)
                .agregarPiso(99)
                .agregarNro(9)
                .build();

        TipoOrganizacionJuridica juridicaEmpresa = new Empresa(244,100000000, "Construccion", new NoComisionista());

        Organizacion empresa = new OrganizacionJuridicaBuilder()
                .agregarCuit("88-88888888-8")
                .agregarDireccionPostal(direccion)
                .agregarTipoDeOrganizacionJjuridica(juridicaEmpresa)
                .agregarNombre("Empresa")
                .agregarRazonSocial("Empresa")
                .agregarNroIGJ("9999")
                .build();

        String username1 = "usuarioA@gmail.com";
        Password password1 = new Password("usuarioA@nyY7");
        UserValidator.checkUsername(username1);
        PasswordValidator.validatePassword(password1.getContent());

        Usuario usuarioAdministrador = new Usuario.Builder()
                .agregarUsername(username1)
                .agregarPassword(password1)
                .agregarIsAdm(true)
                .agregarIsRevisor(true)
                .agregarOrganizacion(empresa)
                .build();

        String username2 = "usuarioB@gmail.com";
        Password password2 = new Password("usuarioB@nyY7");
        UserValidator.checkUsername(username2);
        PasswordValidator.validatePassword(password2.getContent());

        Usuario usuarioComun = new Usuario.Builder()
                .agregarUsername(username2)
                .agregarPassword(password2)
                .agregarIsAdm(false)
                .agregarIsRevisor(false)
                .agregarOrganizacion(empresa)
                .build();


        repoUsuario.agregar(usuarioAdministrador);
        repoUsuario.agregar(usuarioComun);
    }

    //@Test
    public void cargarProveedor() throws IOException, ExcepcionDeCreacionDeDireccion {
        ProductoServicio agua = new ProductoServicio("AGUA","H2O","PRODUCTO");

        DireccionPostal direccion = new DireccionPostal.Builder()
                .agregarPais(repoPais.buscar("Argentina"))
                .agregarProvincia(repoProvincias.buscar("Capital Federal"))
                .agregarCiudad(repoCiudad.buscar("Almagro"))
                .agregarCalle("Medrano")
                .agregarAltura(999)
                .agregarPiso(9)
                .agregarNro(9)
                .build();

        Proveedor proveedor = new Proveedor.Builder()
                .agregarCuit("99-99999999-9")
                .agregarNombre("Proveedor")
                .agregarProductos(agua)
                .agregarDireccionPostal(direccion)
                .build();

        repoProveedor.agregar(proveedor);
    }

    //@Test
    public void cargarEgreso() throws IOException{
        ProductoServicio agua = repoProducto.buscar(1);
        Proveedor proveedor = repoProveedor.buscar(1);
        MedioDePago credito = new MedioDePago("VISA","9999-9999-9999-9999","Credito");
        Usuario usuarioAdm = repoUsuario.buscar("usuarioA@gmail.com");
        Moneda ARS = repoMoneda.buscar("ARS");

        ItemEgreso item = new ItemEgreso.Builder()
                .addProductoServicio(agua)
                .addCantidad(1)
                .addPrecioUnitatio(10.0)
                .addMoneda(ARS)
                .build();

        Egreso egreso = new EgresoBuilder()
                .agregarFechaDeOperacion(LocalDate.now())
                .agregarMedioDePago(credito)
                .agregarUsuario(usuarioAdm)
                .agregarProveedor(proveedor)
                .agregarMoneda(ARS)
                .build();

        repoEgreso.agregar(egreso);
    }

    //@Test
    public void cargarPresupuesto() throws IOException{
        Egreso egreso = repoEgreso.buscar(1);
        Usuario usuarioAdm = repoUsuario.buscar("usuarioA@gmail.com");
        CriterioSeleccion menorCosto = new CriterioMenorCosto();
        ProductoServicio agua = repoProducto.buscar(1);
        Moneda ARS = repoMoneda.buscar("ARS");

        egreso.requerirPresupuesto(menorCosto);

        ItemPresupuesto itemPresupuesto = new ItemPresupuesto.Builder()
                .addProductoServicio(agua)
                .addPrecioUnitatio(1)
                .addCantidad(10)
                .addMoneda(ARS)
                .build();

        Presupuesto presupuesto = new Presupuesto.Builder()
                .agregarEgreso(egreso)
                .agregarMoneda(ARS)
                .build();

        repoPresupuesto.agregar(presupuesto);
        egreso.indicarPresupuestoSeleccionado();
        repoEgreso.modificar(egreso);
    }

    //@Test
    public void cargarIngreso() throws IOException {
        Usuario usuarioAdm = repoUsuario.buscar("usuarioA@gmail.com");
        Moneda ARS = repoMoneda.buscar("ARS");
        Ingreso ingreso = new Ingreso.Builder()
                .agregarUsuario(usuarioAdm)
                .agregarDescripcion("soy un ingreso")
                .agregarMontoTotal(10)
                .agregarMoneda(ARS)
                .agregarFechaDeOperacion(LocalDate.now())
                .agregarFechaDeAceptacion(LocalDate.now())
                .build();

        repoIngreso.agregar(ingreso);
    }
}
