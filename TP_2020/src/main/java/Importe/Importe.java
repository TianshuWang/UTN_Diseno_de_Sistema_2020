package Importe;

import direccion_moneda.mercadolibre_api.molde.Moneda;

import java.math.BigDecimal;

public class Importe {
    private BigDecimal valor;
    private Moneda moneda;

    public Importe(String valor, Moneda moneda){
        this.moneda = moneda;
        this.valor = new BigDecimal(valor);
    }

    //getters setters
    public BigDecimal getValor() {
        return valor;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }
}
