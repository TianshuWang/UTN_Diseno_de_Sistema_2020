package ingreso;

import Importe.Importe;
import direccion_moneda.mercadolibre_api.molde.Moneda;

import java.math.BigDecimal;

public class ItemIngreso {
    private String descripcion;
    private Importe valorUnitario;
    private Integer cantidad;
    private Importe montoTotal;

    public ItemIngreso(String descripcion, String valorUnitario, Moneda moneda, Integer cantidad){
        this.descripcion = descripcion;
        this.valorUnitario = new Importe(valorUnitario,moneda);
        this.cantidad = cantidad;
        BigDecimal total = this.valorUnitario.getValor().multiply(new BigDecimal(this.cantidad));
        this.montoTotal = new Importe(total.toString(),moneda);
    }

    public Importe getMontoTtotal(){
        return this.montoTotal;
    }
}
