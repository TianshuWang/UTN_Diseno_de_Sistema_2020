package tester;
import Importe.Importe;
import direccion_moneda.ConversorDeMoneda;
import direccion_moneda.Ubicacion;
import direccion_moneda.mercadolibre_api.ExcepcionMercadoLibreApi;
import direccion_moneda.mercadolibre_api.molde.Moneda;
import direccion_moneda.mercadolibre_api.services.ServicioMercadolibre;
import direccion_moneda.DireccionPostal;
import egreso.ItemEgreso;
import egreso.MedioDePago;
import egreso.ProductoServicio;
import egreso.documento.Documento;
import egreso.documento.Link;
import egreso.documento.VerificadorDeDocumento;
import egreso.egreso.Egreso;
import egreso.egreso.EgresoFactory;
import egreso.egreso.builder.ExcepcionDeCreacionDeEgreso;
import egreso.proveedor.Proveedor;
import egreso.proveedor.VerificadorDeProveedor;
import exceptions.*;
import ingreso.IngresoFactory;
import ingreso.Ingreso;
import ingreso.ItemIngreso;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import organizacion.*;
import organizacion.categoria.NoComisionista;
import organizacion.organizacionJuridicaBuilder.*;
import persistencia.*;
import presupuesto.PresupuestoFactory;
import presupuesto.ItemPresupuesto;
import presupuesto.Presupuesto;
import usuario.Login;
import usuario.usuario.Administrador;
import usuario.usuario.Usuario;
import usuario.usuario.UsuarioFactory;

import java.io.IOException;
import java.time.LocalDate;

public class Tester {
    //configuracion de Repos
    Repositorio<Egreso> repoEgreso = new Repositorio<Egreso>(new DAOMemoriaEgreso(),Egreso.class);
    Repositorio<Documento> repoDocumento = new Repositorio<Documento>(new DAOMemoriaDocumento(),Documento.class);
    Repositorio<Proveedor> repoProveedor = new Repositorio<Proveedor>(new DAOMemoriaProveedor(),Proveedor.class);
    Repositorio<Ingreso> repoIngreso = new Repositorio<Ingreso>(new DAOMemoriaIngreso(),Ingreso.class);
    Repositorio<Presupuesto> repoPresupuesto = new Repositorio<Presupuesto>(new DAOMemoriaPresupuesto(),Presupuesto.class);
    Repositorio<Usuario> repoUsuario = new Repositorio<Usuario>(new DAOMemoriaUsers(),Usuario.class);

    //configuracion de Organizacion
    Ubicacion almagro = new Ubicacion("Almagro","Capital Federal","Argentina");
    DireccionPostal direccionEmpresa = new DireccionPostal("Medrano",999,3,null,almagro);
    TipoOrganizacionJuridica empresa = new Empresa(244,100000000, "Construccion",new NoComisionista());

    OrganizacionJuridica unaEmpresa = new OrganizacionJuridicaBuilder().agregarNombre("Empresa")
            .agregarCuit("27-67587465-3")
            .agregarDireccionPostal(direccionEmpresa)
            .agregarRazonSocial("Empresa")
            .agregarTipoDeOrganizacionJjuridica(empresa)
            .build();

    Organizacion soyUnaEmpresa = new Organizacion(unaEmpresa);

    //configuracion de producto y proveedor local
    ProductoServicio agua = new ProductoServicio("Agua", "liquido con H2O", "Producto Simple");
    ProductoServicio soda = new ProductoServicio("Soda", "Liquido con H2O con gas", "Producto Simple");
    DireccionPostal direccionProveedor = new DireccionPostal("Medrano",998,2,null,almagro);
    Proveedor proveedor = new Proveedor("Proveedor","34-87234123-7",direccionProveedor,agua,soda);

    //configuracion de medio de pago
    MedioDePago credito = new MedioDePago("Tarjeta De Credito","7654-0987-3344-2214");

    //configuracion de items
    ItemEgreso itemEgreso1 = new ItemEgreso(agua,"5.0",10);
    ItemEgreso itemEgreso2 = new ItemEgreso(soda,"6.0",10);

    //configurar Documento comercial
    Link link = new Link("www.12345.com");
    Documento factura = new Documento(proveedor,link,"123456789");

    //configurar presupuesto
    ItemPresupuesto itemPresupuesto1 = new ItemPresupuesto(agua,"5.0",10);
    ItemPresupuesto itemPresupuesto2 = new ItemPresupuesto(soda,"6.0",10);

    public Tester() throws IOException, ExcepcionDeCreacionDeOrgnizacionBuilder, ExcepcionMercadoLibreApi {
    }

    @Before
    public void init() {
        EgresoFactory.setRepositorio(repoEgreso);
        VerificadorDeDocumento.setRepositorio(repoDocumento);
        VerificadorDeProveedor.setRepositorio(repoProveedor);
        IngresoFactory.setRepositorio(repoIngreso);
        PresupuestoFactory.setRepositorio(repoPresupuesto);
        UsuarioFactory.setRepositorio(repoUsuario);
    }

    @Test
    public void crearUsuarioConPasswordCorrecto() throws PassawordException, IOException, UserException {
        Login.crearUsuario("12345@gmail.com","iuuj7@Ythn8",soyUnaEmpresa,new Administrador());
        Assert.assertEquals("12345@gmail.com", repoUsuario.buscar("12345@gmail.com").getUsername());
    }

    @Test
    public void crearUsuarioConPasswordInCorrecto(){
        try{
            Login.crearUsuario("54321@gmail.com","iuuj7Ythn8",soyUnaEmpresa,new Administrador());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void ingresarUsuarioNoExiste() {
        try{
            Login.crearUsuario("12345@gmail.com","iuuj7@Ythn8",soyUnaEmpresa,new Administrador());
            Usuario user1 = Login.ingresarUsuario("54321@gmail.com","iuuj7@Ythn8");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void ingresarUsuarioConPasswordInCorrecto() throws PassawordException, IOException, UserException {
        try{
            Login.crearUsuario("12345@gmail.com","iuuj7@Ythn8",soyUnaEmpresa,new Administrador());
            Usuario user1 = Login.ingresarUsuario("12345@gmail.com","iuuj@Ythn8");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void cargarEgresoConPreveedorLocal() throws PassawordException, IOException, UserException, DocumentException, PasswordExpired, ExcepcionDeCreacionDeEgreso {
        Login.crearUsuario("12345@gmail.com","iuuj7@Ythn8",soyUnaEmpresa,new Administrador());
        Usuario user1 = Login.ingresarUsuario("12345@gmail.com","iuuj7@Ythn8");
        Egreso egreso1 = user1.cargarEgreso(proveedor,credito, LocalDate.of(2020,7,31),factura, itemEgreso1, itemEgreso2);

        Assert.assertEquals(egreso1.getIdentificador(), EgresoFactory.getRepositorio().buscar(egreso1.getIdentificador()).getIdentificador());
    }

    @Test
    public void calcularCategoriaEmpresa(){
        Assert.assertEquals("Mediana Tramo 1",soyUnaEmpresa.getTipoDeOrganizacion().getCategoria());
    }

    @Test
    public void crearUbicacionConMercadoLibreApi() throws IOException,ExcepcionMercadoLibreApi {
        Ubicacion laPlata = new Ubicacion("La Plata","Buenos Aires","Argentina");
        Assert.assertEquals("La Plata",laPlata.getCiudad().name);
        Assert.assertEquals("Buenos Aires",laPlata.getProvincia().name);
        Assert.assertEquals("Argentina",laPlata.getPais().name);
        Assert.assertEquals("ARS",laPlata.getMoneda().id);
    }

    @Test
    public void crearUbicacionConMercadoLibreApiCiudadInexistente(){
        try{
            Ubicacion nada = new Ubicacion("Nada","Buenos Aires","Argentina");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void conversionDeMonedaConMercadoLibreApi() throws IOException, ExcepcionMercadoLibreApi {
        Ubicacion laPlata = new Ubicacion("La Plata","Buenos Aires","Argentina");
        Ubicacion newYork = new Ubicacion("New York","New York","United States of America");

        Moneda ARS = laPlata.getMoneda();
        Moneda USD = newYork.getMoneda();

        Importe importe = new Importe("10",USD);
        Assert.assertEquals("USD",importe.getMoneda().id);
        Assert.assertEquals("10",importe.getValor().toString());

        ConversorDeMoneda.instancia().convertir(importe,ARS);
        Assert.assertEquals("ARS",importe.getMoneda().id);
        Assert.assertEquals("725.70",importe.getValor().toString());
    }

    @Test
    public void conversionDeMonedaDeEgreso() throws IOException, ExcepcionMercadoLibreApi, PassawordException, UserException, PasswordExpired, ExcepcionDeCreacionDeEgreso, DocumentException {
        ProductoServicio agua = new ProductoServicio("Agua", "liquido con H2O", "Producto Simple");
        Ubicacion newYork = new Ubicacion("New York","New York","United States of America");
        DireccionPostal direccionProveedorUS = new DireccionPostal("XXXXX",999,5,101,newYork);
        Proveedor proveedorUS = new Proveedor("ProveedorUS","99999999",direccionProveedorUS,agua);

        //configurar un item de egreso con un proveedor en USD
        ItemEgreso itemEgreso = new ItemEgreso(agua,"10",10);
        Assert.assertEquals("USD",itemEgreso.getMontoTtotal().getMoneda().id);
        Assert.assertEquals("100",itemEgreso.getMontoTtotal().getValor().toString());

        Login.crearUsuario("12345@gmail.com","iuuj7@Ythn8",soyUnaEmpresa,new Administrador());
        Usuario user = Login.ingresarUsuario("12345@gmail.com","iuuj7@Ythn8");

        //cargar un egreso con el item previo
        Egreso egreso = user.cargarEgreso(proveedorUS,credito, LocalDate.of(2020,7,31),null, itemEgreso);
        Assert.assertEquals("ARS",egreso.getMontoTotal().getMoneda().id);
        Assert.assertEquals("7257.00",egreso.getMontoTotal().getValor().toString());
    }

    @Test
    public void conversionDeMonedaDeIngreso() throws IOException, ExcepcionMercadoLibreApi {
        Ubicacion newYork = new Ubicacion("New York","New York","United States of America");
        Moneda USD = newYork.getMoneda();

        //configurar un item de ingreso con USD
        ItemIngreso itemIngreso = new ItemIngreso("agua","10",USD,10);
        Assert.assertEquals("USD",itemIngreso.getMontoTtotal().getMoneda().id);
        Assert.assertEquals("100",itemIngreso.getMontoTtotal().getValor().toString());

        Ingreso ingreso = new Ingreso(soyUnaEmpresa,"Venta",LocalDate.of(2020,7,31),itemIngreso);
        Assert.assertEquals("ARS",ingreso.getMontoTotal().getMoneda().id);
        Assert.assertEquals("7257.00",ingreso.getMontoTotal().getValor().toString());
    }

    @Test
    public void conversionDeMonedaEgresoYPresupuesto() throws PassawordException, IOException, UserException, PasswordExpired, ExcepcionDeCreacionDeEgreso, DocumentException, ExcepcionMercadoLibreApi {
        //cargar un egreso con item agua con proveedor local
        Login.crearUsuario("12345@gmail.com","iuuj7@Ythn8",soyUnaEmpresa,new Administrador());
        Usuario user = Login.ingresarUsuario("12345@gmail.com","iuuj7@Ythn8");
        Egreso egreso = user.cargarEgreso(proveedor,credito, LocalDate.of(2020,7,31),factura, itemEgreso1);

        //configurar un proveedor de EEUU con item agua
        ProductoServicio agua = new ProductoServicio("Agua", "liquido con H2O", "Producto Simple");
        Ubicacion newYork = new Ubicacion("New York","New York","United States of America");
        DireccionPostal direccionProveedorUS = new DireccionPostal("XXXXX",999,5,101,newYork);
        Proveedor proveedorUS = new Proveedor("ProveedorUS","99999999",direccionProveedorUS,agua);

        ItemPresupuesto itemPresupuesto = new ItemPresupuesto(agua,"0.1",10);
        Assert.assertEquals("USD",itemPresupuesto.getMontoTotal().getMoneda().id);
        Assert.assertEquals("1.0",itemPresupuesto.getMontoTotal().getValor().toString());

        Presupuesto presupuesto = PresupuestoFactory.crearPresupuesto(egreso,itemPresupuesto);
        Assert.assertEquals("ARS",itemPresupuesto.getMontoTotal().getMoneda().id);
        Assert.assertEquals("72.570",itemPresupuesto.getMontoTotal().getValor().toString());
    }

}
