package presupuesto;

import com.google.gson.annotations.Expose;
import converters.LocalDateAttributeConverter;
import direccion_moneda.mercadolibre_api.model.Moneda;
import egreso.Egreso;
import egreso.Proveedor;
import entityPersistente.EntidadPersistente;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import organizacion.criterio.CategoriaClasificacion;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "presupuesto")
public class Presupuesto extends EntidadPersistente {

    @Column(name = "fecha_presupuesto")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaPresupuesto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "egreso_id", referencedColumnName = "id")
    private Egreso egreso;

    @Column(name = "monto_total")
    private Double montoTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "moneda_id", referencedColumnName = "id")
    private Moneda moneda;

    @OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<ItemPresupuesto> itemPresupuestoList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<CategoriaClasificacion> categoriasList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;

    private Presupuesto(){

    }

    public Presupuesto(Builder builder) throws IOException {
        this.fechaPresupuesto = builder.fechaPresupuesto;
        this.egreso = builder.egreso;
        this.itemPresupuestoList = builder.itemPresupuestos;
        this.categoriasList = builder.categoriasList;
        this.moneda = builder.moneda;
        this.proveedor = builder.proveedor;
        calcularMontoTotal();
    }

    public static class Builder{
        private LocalDate fechaPresupuesto;
        private Egreso egreso;
        private Moneda moneda;
        private List<ItemPresupuesto> itemPresupuestos;
        private List<CategoriaClasificacion> categoriasList;
        private Proveedor proveedor;

        public Builder(){
            this.itemPresupuestos = new ArrayList<>();
            this.categoriasList = new ArrayList<>();
        }

        public Builder agregarFechaPresupuesto(LocalDate fecha){
            this.fechaPresupuesto = fecha;
            return this;
        }

        public Builder agregarEgreso(Egreso egreso){
            this.egreso = egreso;
            return this;
        }

        public Builder agregarItemsPresupuesto(List<ItemPresupuesto>items){
            itemPresupuestos.addAll(items);
            return this;
        }

        public Builder agregarMoneda(Moneda moneda){
            this.moneda = moneda;
            return this;
        }

        public Builder agregarProveedor(Proveedor proveedor){
            this.proveedor = proveedor;
            return this;
        }

        public Presupuesto build() throws IOException {
            Presupuesto presupuesto = new Presupuesto(this);
            this.itemPresupuestos.forEach(i->i.setPresupuesto(presupuesto));
            this.egreso.addPresupuestosRequeridos(presupuesto);
            return presupuesto;
        }
    }

    private void calcularMontoTotal(){
        this.montoTotal = itemPresupuestoList.stream().mapToDouble(ItemPresupuesto::getMontoTotal).sum();
    }

    //public void agregarCategoriasClasificacion(CategoriaClasificacion...categoriaClasificacions){
    //Collections.addAll(categoriasList,categoriaClasificacions);
    //}

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    //getters

    public Moneda getMoneda() { return moneda; }

    public LocalDate getFechaPresupuesto() { return fechaPresupuesto; }

    public Egreso getEgreso() {
        return egreso;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public List<ItemPresupuesto> getItemPresupuestoList() {
        return itemPresupuestoList;
    }

    public List<CategoriaClasificacion> getCategoriasList() {
        return categoriasList;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    // setters

}