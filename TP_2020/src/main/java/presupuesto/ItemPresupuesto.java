package presupuesto;

import Importe.Importe;
import direccion_moneda.mercadolibre_api.molde.Moneda;
import direccion_moneda.mercadolibre_api.molde.Ratio;
import egreso.ProductoServicio;
import egreso.egreso.Egreso;

import java.math.BigDecimal;

public class ItemPresupuesto {
    private ProductoServicio productoServicio;
    private Importe precio_unitario;
    private Integer cantidad;
    private Egreso egreso;
    private Importe montoTotal;

    public ItemPresupuesto(ProductoServicio productoServicio,String valorUnitario, Integer cantidad){
        this.productoServicio = productoServicio;
        Moneda moneda = this.productoServicio.getMoneda();
        this.precio_unitario = new Importe(valorUnitario,moneda);
        this.cantidad = cantidad;
        BigDecimal total = this.precio_unitario.getValor().multiply(new BigDecimal(this.cantidad));
        this.montoTotal = new Importe(total.toString(),moneda);
    }

    //getters setters
    public ProductoServicio getProductoServicio() {
        return productoServicio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Importe getMontoTotal() {
        return montoTotal;
    }

    public void setEgreso(Egreso egreso) {
        this.egreso = egreso;
    }
}
