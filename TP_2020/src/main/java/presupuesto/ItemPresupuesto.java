package presupuesto;

import direccion_moneda.mercadolibre_api.model.Moneda;
import egreso.ProductoServicio;
import entityPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "itemPresupuesto")
public class ItemPresupuesto extends EntidadPersistente {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private ProductoServicio productoServicio;

    @Column(name = "precio_unitario")
    private double precioUnitario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "moneda_id", referencedColumnName = "id")
    private Moneda moneda;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "monto_total")
    private Double montoTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "presupuesto_id", referencedColumnName = "id")
    private Presupuesto presupuesto;

    private ItemPresupuesto(){

    }

    public ItemPresupuesto(Builder builder){
        this.productoServicio = builder.productoServicio;
        this.precioUnitario = builder.precioUnitario;
        this.cantidad = builder.cantidad;
        this.moneda = builder.moneda;
        this.montoTotal = this.cantidad * this.precioUnitario;
    }

    public static class Builder{
        private ProductoServicio productoServicio;
        private double precioUnitario;
        private Integer cantidad;
        private Moneda moneda;

        public Builder addProductoServicio(ProductoServicio productoServicio){
            this.productoServicio = productoServicio;
            return this;
        }

        public Builder addPrecioUnitatio(double precioUnitario){
            this.precioUnitario = precioUnitario;
            return this;
        }

        public Builder addCantidad(int cantidad){
            this.cantidad = cantidad;
            return this;
        }

        public Builder addMoneda(Moneda moneda){
            this.moneda = moneda;
            return this;
        }

        public ItemPresupuesto build(){
            return new ItemPresupuesto(this);
        }
    }

    //getters setters

    public Moneda getMoneda() { return moneda; }

    public ProductoServicio getProductoServicio() {
        return productoServicio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }
}