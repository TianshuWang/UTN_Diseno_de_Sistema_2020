package egreso;

import Importe.Importe;
import direccion_moneda.mercadolibre_api.molde.Moneda;

import java.math.BigDecimal;

public class ItemEgreso {
    private ProductoServicio productoServicio;
    private Importe precioUnitario;
    private Integer cantidad;
    private Importe montoTotal;

    public ItemEgreso(ProductoServicio productoServicio, String valorUnitario, Integer cantidad){
        this.productoServicio = productoServicio;
        Moneda moneda = this.productoServicio.getMoneda();
        this.precioUnitario = new Importe(valorUnitario,moneda);
        this.cantidad = cantidad;
        BigDecimal total = this.precioUnitario.getValor().multiply(new BigDecimal(this.cantidad));
        this.montoTotal = new Importe(total.toString(),moneda);
    }

    public Importe getMontoTtotal(){
        return this.montoTotal;
    }

    public ProductoServicio getProductoServicio() {
        return productoServicio;
    }

    public int getCantidad() {
        return cantidad;
    }
}
