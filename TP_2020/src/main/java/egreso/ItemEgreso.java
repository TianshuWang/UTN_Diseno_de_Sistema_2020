package egreso;

import direccion_moneda.mercadolibre_api.model.Moneda;
import entityPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "itemEgreso")
public class ItemEgreso extends EntidadPersistente {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private ProductoServicio productoServicio;

    @Column(name = "precio_unitario")
    private double precioUnitario;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "monto_total")
    private double montoTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "moneda_id", referencedColumnName = "id")
    private Moneda moneda;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "egreso_id", referencedColumnName = "id")
    private Egreso egreso;

    private ItemEgreso(){

    }

    public ItemEgreso(Builder builder){
        this.productoServicio = builder.productoServicio;
        this.precioUnitario = builder.precioUnitario;
        this.cantidad = builder.cantidad;
        this.moneda = builder.moneda;
        this.montoTotal = this.precioUnitario * this.cantidad;
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

        public ItemEgreso build(){
            return new ItemEgreso(this);
        }
    }

    //getters and setters
    public void setEgreso(Egreso egreso) {
        this.egreso = egreso;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public ProductoServicio getProductoServicio() {
        return productoServicio;
    }

    public int getCantidad() {
        return cantidad;
    }
}
