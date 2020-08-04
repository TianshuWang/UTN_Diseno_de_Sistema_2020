package egreso.egreso;
import Importe.Importe;
import egreso.*;
import egreso.documento.*;
import egreso.proveedor.*;
import exceptions.UserException;
import generadorIdentificador.GeneradorIdentificador;
import ingreso.Ingreso;
import organizacion.*;
import organizacion.criterio.CategoriaClasificacion;
import presupuesto.criterioSeleccion.CriterioSeleccion;
import presupuesto.Presupuesto;
import usuario.usuario.Usuario;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Egreso {
    private String identificador;
    private Proveedor proveedor;
    private Documento documento;
    private MedioDePago medioDePago;
    private List<ItemEgreso> itemEgresos;
    private LocalDate fechaDeOperacion;
    private Importe montoTotal;
    private Organizacion organizacion;
    private List<Presupuesto> presupuestosRequeridos;
    private Presupuesto presupuestoSeleccionado;
    private CriterioSeleccion criterioSeleccion;
    private List<Usuario> revisores;
    private Boolean validado;
    private List<CategoriaClasificacion> categoriasList;
    private Ingreso ingresoAsociado;

    public Egreso(){
        this.presupuestosRequeridos = new ArrayList<>();
        this.identificador = GeneradorIdentificador.generarIdentificadorEgreso("%05d");
        this.revisores = new ArrayList<>();
        this.categoriasList = new ArrayList<>();
        this.validado = false;
    }

    public void requerirPresupuesto(CriterioSeleccion criterioSeleccion, Usuario...revisores) throws FileNotFoundException, UserException, UserException {
        this.criterioSeleccion = criterioSeleccion;
        vistaHabilitadaPara(revisores);
    }

    private void vistaHabilitadaPara(Usuario...users) throws UserException {
        for(Usuario u:users){
            if(!esUsuarioDeOrganizacion(u)){
                throw new UserException(u);
            }
            Collections.addAll(revisores,u);
        }
    }

    private boolean esUsuarioDeOrganizacion(Usuario user){
        return user.getOrganizacion() == organizacion;
    }

    public void agregarCategoriasClasificacion(CategoriaClasificacion ...categoriaClasificacions){
        Collections.addAll(categoriasList,categoriaClasificacions);
    }

    //getters
    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public Presupuesto getPresupuestoSeleccionado() {
        return presupuestoSeleccionado;
    }

    public List<ItemEgreso> getItemEgresos() {
        return itemEgresos;
    }

    public CriterioSeleccion getCriterioSeleccion() {
        return criterioSeleccion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public List<Usuario> getRevisores() {
        return revisores;
    }

    public List<Presupuesto> getPresupuestosRequeridos() {
        return presupuestosRequeridos;
    }

    public Importe getMontoTotal() {
        return this.montoTotal;
    }

    public Boolean getValidado() {
        return validado;
    }

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public LocalDate getFechaDeOperacion() {
        return fechaDeOperacion;
    }

    //setters
    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public void setPresupuestoSeleccionado(Presupuesto presupuestoSeleccionado) {
        this.presupuestoSeleccionado = presupuestoSeleccionado;
    }

    public void setIngresoAsociado(Ingreso ingresoAsociado){
        this.ingresoAsociado = ingresoAsociado;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public void setItemEgresos(List<ItemEgreso> itemEgresos) {
        this.itemEgresos = itemEgresos;
    }

    public void setFechaDeOperacion(LocalDate fechaDeOperacion) {
        this.fechaDeOperacion = fechaDeOperacion;
    }

    public void setMontoTotal(Importe valorTotal) {
        this.montoTotal = valorTotal;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public void setPresupuestosRequeridos(List<Presupuesto> presupuestosRequeridos) {
        this.presupuestosRequeridos = presupuestosRequeridos;
    }

    public void setCriterioSeleccion(CriterioSeleccion criterioSeleccion) {
        this.criterioSeleccion = criterioSeleccion;
    }

    public void setRevisores(List<Usuario> revisores) {
        this.revisores = revisores;
    }

    public void setCategoriasList(List<CategoriaClasificacion> categoriasList) {
        this.categoriasList = categoriasList;
    }


}
