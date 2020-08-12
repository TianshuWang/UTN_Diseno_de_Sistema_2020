package tester;

import direccion_moneda.Ubicacion;
import direccion_moneda.mercadolibre_api.ExcepcionMercadoLibreApi;
import direccion_moneda.mercadolibre_api.molde.Moneda;
import direccion_moneda.mercadolibre_api.services.ServicioMercadolibre;
import bandejaDeMensaje.criterioFiltracion.*;
import direccion_moneda.DireccionPostal;
import egreso.documento.VerificadorDeDocumento;
import egreso.egreso.*;
import egreso.egreso.builder.ExcepcionDeCreacionDeEgreso;
import egreso.proveedor.VerificadorDeProveedor;
import ingreso.IngresoFactory;
import ingreso.Ingreso;
import ingreso.ItemIngreso;
import organizacion.categoria.*;
import organizacion.organizacionJuridicaBuilder.*;
import persistencia.*;
import presupuesto.*;
import presupuesto.criterioSeleccion.CriterioMenorCosto;
import presupuesto.scheduler.Scheduler1;
import usuario.usuario.Administrador;
import usuario.usuario.Comun;
import usuario.usuario.Usuario;
import exceptions.*;
import egreso.*;
import egreso.documento.Documento;
import egreso.documento.Link;
import egreso.proveedor.Proveedor;
import organizacion.*;
import usuario.*;
import organizacion.criterio.CriterioClasificacion;
import organizacion.criterio.CategoriaClasificacion;
import usuario.usuario.UsuarioFactory;

import java.io.IOException;
import java.time.LocalDate;

public class Tester_Schedule {
    public static void main(String[] args) throws IOException, UserException, InterruptedException, OrganizacionException, UserException, EgresoException, IngresoException, CriterioException, RolException, PresupuestoException, PassawordException, DocumentException, PasswordExpired, ExcepcionDeCreacionDeOrgnizacionBuilder, ExcepcionDeCreacionDeEgreso, ExcepcionMercadoLibreApi {
        //configuracion de Repos
        Repositorio<Egreso> repoEgreso = new Repositorio<Egreso>(new DAOMemoriaEgreso(),Egreso.class);
        EgresoFactory.setRepositorio(repoEgreso);
        Repositorio<Documento> repoDocumento = new Repositorio<Documento>(new DAOMemoriaDocumento(),Documento.class);
        VerificadorDeDocumento.setRepositorio(repoDocumento);
        Repositorio<Proveedor> repoProveedor = new Repositorio<Proveedor>(new DAOMemoriaProveedor(),Proveedor.class);
        VerificadorDeProveedor.setRepositorio(repoProveedor);
        Repositorio<Ingreso> repoIngreso = new Repositorio<Ingreso>(new DAOMemoriaIngreso(),Ingreso.class);
        IngresoFactory.setRepositorio(repoIngreso);
        Repositorio<Presupuesto> repoPresupuesto = new Repositorio<Presupuesto>(new DAOMemoriaPresupuesto(),Presupuesto.class);
        PresupuestoFactory.setRepositorio(repoPresupuesto);
        Repositorio<Usuario> repoUsuario = new Repositorio<Usuario>(new DAOMemoriaUsers(),Usuario.class);
        UsuarioFactory.setRepositorio(repoUsuario);

        //configuracion de Organizacion
        ServicioMercadolibre servicioMercadolibre = ServicioMercadolibre.instancia();

        Ubicacion caba = new Ubicacion("Almagro","Capital Federal","Argentina");
        DireccionPostal direccionEmpresa = new DireccionPostal("Medrano",999,3,null,caba);
        TipoOrganizacionJuridica empresa = new Empresa(244,100000000, "Construccion",new NoComisionista());



        OrganizacionJuridica unaEmpresa = new OrganizacionJuridicaBuilder().agregarNombre("Empresa")
                                                                            .agregarCuit("27-67587465-3")
                                                                            .agregarDireccionPostal(direccionEmpresa)
                                                                            .agregarRazonSocial("Empresa")
                                                                            .agregarTipoDeOrganizacionJjuridica(empresa)
                                                                            .build();

        Organizacion soyUnaEmpresa = new Organizacion(unaEmpresa);

        //configuracion de producto y proveedor
        ProductoServicio agua = new ProductoServicio("Agua", "liquido con H2O", "Producto Simple");
        ProductoServicio soda = new ProductoServicio("Soda", "Liquido con H2O con gas", "Producto Simple");
        DireccionPostal direccionProveedor = new DireccionPostal("Medrano",998,2,null,caba);
        Proveedor proveedor = new Proveedor("Proveedor","34-87234123-7",direccionProveedor,agua,soda);

        //configuracion de medio de pago
        MedioDePago credito = new MedioDePago("Tarjeta De Credito","7654-0987-3344-2214");

        //configuracion de items
        ItemEgreso itemEgreso1 = new ItemEgreso(agua,"5.0",10);
        ItemEgreso itemEgreso2 = new ItemEgreso(soda,"6.0",10);

        //configurar Documento comercial
        Link link = new Link("www.12345.com");
        Documento factura = new Documento(proveedor,link,"123456789");

        //configuracion de los usuarios
        Login.crearUsuario("12345@gmail.com","iuuj7@Ythn8",soyUnaEmpresa,new Administrador());
        Login.crearUsuario("54321@gmail.com","jjfu7@uuyT",soyUnaEmpresa,new Comun());

        //cargar egresos
        Usuario user1 = Login.ingresarUsuario("12345@gmail.com","iuuj7@Ythn8");
        Usuario user2 = Login.ingresarUsuario("54321@gmail.com","jjfu7@uuyT");
        Egreso egreso1 = user1.cargarEgreso(proveedor,credito,LocalDate.of(2020,06,20),factura, itemEgreso1, itemEgreso2);

        //configurar presupuesto
        ItemPresupuesto itemPresupuesto1 = new ItemPresupuesto(agua,"5.0",10);
        ItemPresupuesto itemPresupuesto2 = new ItemPresupuesto(soda,"6.0",10);

        //cargar presupuestos
        egreso1.requerirPresupuesto(new CriterioMenorCosto(),user1,user2);
        Presupuesto presupuesto1 = user1.cargarPresupuesto(egreso1,itemPresupuesto1,itemPresupuesto2);
        Presupuesto presupuesto2 = user1.cargarPresupuesto(egreso1,itemPresupuesto1);

        //Scheduler con Runnable
        //Scheduler scheduler = new Scheduler(new RunValidator(user1,user2));
        //scheduler.run();

        //Scheduler con Timer
        Scheduler1 scheduler1 = new Scheduler1();
        scheduler1.run(soyUnaEmpresa);

        //ingreso
        Moneda moneda = soyUnaEmpresa.getTipoDeOrganizacion().getDireccionPostal().getUbicacion().getMoneda();
        ItemIngreso itemIngreso1 = new ItemIngreso("agua","10.0",moneda,5);
        Ingreso ingreso1 = user1.cargarIngreso("Venta de Agua",LocalDate.now(),itemIngreso1);

        //asociacion de ingreso al egreso
        user1.asociarEgresoIngreso(egreso1,ingreso1);

        //configuracion de categoria y criterio
        CriterioClasificacion alcanceProyecto = user1.crearCriterioClasificacion("Alcance Del Proyecto");
        CriterioClasificacion tamanioProyecto = user1.crearCriterioClasificacion("Tamaño Del Proyecto");
        CategoriaClasificacion nacional = user1.crearCategoriaClasificacion("Nacional",alcanceProyecto);
        CategoriaClasificacion micro = user1.crearCategoriaClasificacion("Micro",tamanioProyecto);

        //tamanioProyecto como criterio hijo del criterio alcanceProyecto
        user1.otorgarJerarquiaAcriterios(alcanceProyecto,tamanioProyecto);

        //asociacion al egreso1 las categorias del alcanceProyecto
        user1.asociarCategoriaClasificacionEgreso(egreso1,nacional,micro);
        user2.asociarCategoriaClasificacionPresupuesto(presupuesto1,nacional,micro);

        //espera que el Scheduler ejecuta y ver los resultados
        Thread.sleep(5000);
        user1.verResultadosDeValidacionesPorFiltracion(user1, new FiltracionFecha(LocalDate.of(2020,7,31)));
        user2.verResultadosDeValidacionesPorFiltracion(user1, new FiltracionFecha(LocalDate.of(2020,7,31)));
    }
}
