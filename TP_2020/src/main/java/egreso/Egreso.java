package egreso;
import com.google.gson.annotations.Expose;
import converters.CriterioSeleccionConverter;
import converters.LocalDateAttributeConverter;
import direccion_moneda.mercadolibre_api.model.Moneda;
import egreso.documento.*;
import entityPersistente.EntidadPersistente;
import ingreso.Ingreso;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import organizacion.criterio.CategoriaClasificacion;
import presupuesto.criterioSeleccion.CriterioSeleccion;
import presupuesto.Presupuesto;
import usuario.usuario.Usuario;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "egreso")
public class Egreso extends EntidadPersistente {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario", referencedColumnName = "username")
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documento_id")
    private Documento documento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medio_pago_id", referencedColumnName = "id")
    private MedioDePago medioDePago;

    @OneToMany(mappedBy = "egreso", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<ItemEgreso> itemEgresos;

    @Column(name = "fecha_operacion")
    @Convert(converter = LocalDateAttributeConverter.class)
    @Expose(serialize = true, deserialize = true)
    private LocalDate fechaDeOperacion;

    @Expose(serialize = true, deserialize = true)
    @Column(name = "monto_total")
    private double montoTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "moneda_id")
    private Moneda moneda;

    @OneToMany(mappedBy = "egreso", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Presupuesto> presupuestosRequeridos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "presupuesto_id")
    private Presupuesto presupuestoSeleccionado;

    @Column(name = "criterio_seleccion")
    @Convert(converter = CriterioSeleccionConverter.class)
    private CriterioSeleccion criterioSeleccion;

    @Column(name = "requerir_presupuesto")
    
    private Boolean requerirPresupuesto;

    @Column(name = "validado")
    
    private Boolean validado;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<CategoriaClasificacion> categoriasList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingreso_id", referencedColumnName = "id")
    private Ingreso ingresoAsociado;

    public Egreso(){
        this.presupuestosRequeridos = new ArrayList<>();
        this.categoriasList = new ArrayList<>();
        this.requerirPresupuesto = false;
        this.validado = false;
    }

    public void requerirPresupuesto(CriterioSeleccion criterioSeleccion, Usuario...revisores) {
        this.criterioSeleccion = criterioSeleccion;
        //this.requerirPresupuesto = true;
    }

    public void indicarPresupuestoSeleccionado(){
        this.presupuestoSeleccionado = this.criterioSeleccion.seleccionarPresupuesto(this);
    }

    public void addPresupuestosRequeridos(Presupuesto ... presupuestos) {
        Collections.addAll(this.presupuestosRequeridos,presupuestos);
    }

    public void agregarCategoriasClasificacion(List<CategoriaClasificacion> categoriasClasificacions){
        categoriasList.addAll(categoriasClasificacions);
    }

    //getters and setters
    public Presupuesto getPresupuestoSeleccionado() {
        return presupuestoSeleccionado;
    }

    public List<ItemEgreso> getItemEgresos() {
        return itemEgresos;
    }

    public CriterioSeleccion getCriterioSeleccion() {
        return criterioSeleccion;
    }

    public Boolean getRequerirPresupuesto() {
        return requerirPresupuesto;
    }

    public List<Presupuesto> getPresupuestosRequeridos() {
        return presupuestosRequeridos;
    }

    public Boolean getValidado() {
        return validado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public LocalDate getFechaDeOperacion() {
        return fechaDeOperacion;
    }

    public Ingreso getIngresoAsociado() {
        return ingresoAsociado;
    }

    public List<CategoriaClasificacion> getCategoriasList() {
        return categoriasList;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
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

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setIngresoAsociado(Ingreso ingresoAsociado) {
        this.ingresoAsociado = ingresoAsociado;
    }

    public void setRequerirPresupuesto(boolean valor) {
        this.requerirPresupuesto = valor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Documento getDocumento() {
        return documento;
    }

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public List<CategoriaClasificacion> getCategoriaClasificacion() {
        return categoriasList;
    }
}
