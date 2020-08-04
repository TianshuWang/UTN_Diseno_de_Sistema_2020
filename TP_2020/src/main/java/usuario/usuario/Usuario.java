package usuario.usuario;
import bandejaDeMensaje.BandejaDeMensaje;
import bandejaDeMensaje.DAOMemoriaBandeja;
import bandejaDeMensaje.criterioFiltracion.CriterioFiltracion;
import bandejaDeMensaje.mensaje.MostradorMensaje;
import direccion_moneda.mercadolibre_api.services.ServicioMercadolibre;
import egreso.egreso.builder.ExcepcionDeCreacionDeEgreso;
import ingreso.IngresoFactory;
import ingreso.Ingreso;
import ingreso.ItemIngreso;
import organizacion.criterio.CriterioClasificacion;
import organizacion.criterio.CategoriaClasificacion;
import egreso.egreso.*;
import exceptions.*;
import presupuesto.*;
import usuario.password.Password;
import egreso.*;
import egreso.documento.Documento;
import egreso.proveedor.Proveedor;
import organizacion.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Usuario {
    private String username;
    private Password password;
    private Organizacion organizacion;
    private Rol rol;
    private List<Egreso> egresoList;
    private List<Ingreso> ingresoList;
    private BandejaDeMensaje bandejaValidacion;

    public Usuario(String username, String password, Organizacion organizacion, Rol rol){
        this.username = username;
        this.password = new Password(password);
        this.organizacion = organizacion;
        this.rol = rol;
        this.egresoList = new ArrayList<>();
        this.ingresoList = new ArrayList<>();
        this.bandejaValidacion = new BandejaDeMensaje(new DAOMemoriaBandeja());
    }

    public Usuario crearUsuario(String username, String password, Organizacion organizacion, Rol rol) throws UserException, IOException, RolException,PassawordException {
        return rol.crearUsuario(username,password,organizacion,rol);
    }

    public Egreso cargarEgreso(Proveedor proveedor, MedioDePago medioDePago, LocalDate fechaDeOperacion, Documento documento, ItemEgreso... itemEgresos) throws IOException, DocumentException, ExcepcionDeCreacionDeEgreso {
        Egreso egresoNuevo = EgresoFactory.crearEgreso(organizacion,proveedor,medioDePago,fechaDeOperacion,documento,itemEgresos);
        egresoList.add(egresoNuevo);
        egresoNuevo.getRevisores().add(this);
        return egresoNuevo;
    }

    public Presupuesto cargarPresupuesto(Egreso egreso, ItemPresupuesto ...items) throws IOException, EgresoException {
        if(GetCantidadPresupuestos.getCantidad() == 0){
            throw new EgresoException();
        }
        Presupuesto presupuesto = PresupuestoFactory.crearPresupuesto(egreso,items);
        egreso.getPresupuestosRequeridos().add(presupuesto);
        indicarPresupuestoSeleccionado(egreso);
        return presupuesto;
    }

    private void indicarPresupuestoSeleccionado(Egreso egreso){
        Presupuesto seleccionado = egreso.getCriterioSeleccion().seleccionarPresupuesto(egreso);
        egreso.setPresupuestoSeleccionado(seleccionado);
    }

    public void verResultadosDeValidacionesPorFiltracion(Usuario user, CriterioFiltracion criterioFiltracion) throws RolException {
        if(!esRevisorDe(user)){
            throw new RolException(user);
        }
        user.getBandejaValidacion().filtrar(criterioFiltracion).forEach(m-> MostradorMensaje.mostrar(m));
    }

    private boolean esRevisorDe(Usuario user){
        return user.getEgresoList().stream().anyMatch(e->e.getRevisores().contains(this));
    }

    public Ingreso cargarIngreso(String descripcion, LocalDate fechaDeOperacion, ItemIngreso ...itemsIngreso) throws IOException{
        Ingreso ingresoNuevo = IngresoFactory.crearIngreso(this.organizacion,descripcion,fechaDeOperacion,itemsIngreso);
        ingresoList.add(ingresoNuevo);
        return ingresoNuevo;
    }

    public CriterioClasificacion crearCriterioClasificacion(String descripcion){
        return organizacion.crearCriterioClasificacion(descripcion);
    }

    public CategoriaClasificacion crearCategoriaClasificacion(String descripcion, CriterioClasificacion criterio) throws CriterioException {
        return organizacion.crearCategoriaClasificacion(descripcion,criterio);
    }

    public void otorgarJerarquiaAcriterios(CriterioClasificacion padre, CriterioClasificacion hijo) throws RolException, CriterioException {
        if(!padre.getOrganizacion().equals(organizacion) || !hijo.getOrganizacion().equals(organizacion)){
            throw new CriterioException();
        }
        rol.otorgarJerarquiaAcriterios(padre,hijo);
    }

    public void asociarCategoriaClasificacionEgreso(Egreso egreso,CategoriaClasificacion ...categorias) throws EgresoException{
        if(!egreso.getOrganizacion().equals(organizacion)){
            throw new EgresoException(organizacion);
        }
        egreso.agregarCategoriasClasificacion(categorias);
    }

    public void asociarCategoriaClasificacionPresupuesto(Presupuesto presupuesto,CategoriaClasificacion ...categoria) throws PresupuestoException {
        if(!presupuesto.getEgreso().getOrganizacion().equals(organizacion)){
            throw new PresupuestoException("Presupuesto No Existe De Un Egreso En La Organizacion");
        }
        presupuesto.agregarCategoriasClasificacion(categoria);
    }

    public void asociarEgresoIngreso(Egreso egreso, Ingreso ingreso) throws EgresoException, IngresoException {
        if(!egreso.getOrganizacion().equals(organizacion)){
            throw new EgresoException(organizacion);
        }
        if(!ingreso.getOrganizacion().equals(organizacion)){
            throw new IngresoException("Ingreso No Existe En La Organizacion");
        }
        egreso.setIngresoAsociado(ingreso);
    }

    //getters
    public String getUsername() {
        return username;
    }

    public String getIdentificador() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public List<Egreso> getEgresoList() {
        return egresoList;
    }

    public BandejaDeMensaje getBandejaValidacion() {
        return bandejaValidacion;
    }
}
