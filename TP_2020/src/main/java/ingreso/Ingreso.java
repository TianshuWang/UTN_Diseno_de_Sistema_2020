package ingreso;

import Importe.Importe;
import direccion_moneda.mercadolibre_api.molde.Moneda;
import generadorIdentificador.GeneradorIdentificador;
import organizacion.Organizacion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ingreso {
    private String identificador;
    private String descripcion;
    private List<ItemIngreso> itemIngresoList;
    private Importe valorTotal;
    private Organizacion organizacion;
    private LocalDate fechaDeOperacion;

    public Ingreso(Organizacion organizacion, String descripcion, LocalDate fechaDeOperacion, ItemIngreso ...itemIngreso){
        this.organizacion = organizacion;
        this.fechaDeOperacion = fechaDeOperacion;
        this.descripcion = descripcion;
        this.itemIngresoList = new ArrayList<>();
        Collections.addAll(itemIngresoList,itemIngreso);
        Moneda moneda = itemIngreso[0].getMontoTtotal().getMoneda();
        this.valorTotal = new Importe(calcularValorTotal().toString(),moneda);
        this.identificador = GeneradorIdentificador.generarIdentificadorIngreso("%05d");
    }

    private BigDecimal calcularValorTotal(){
        return itemIngresoList.stream().map(i->i.getMontoTtotal().getValor()).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    //getters
    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public String getIdentificador() {
        return identificador;
    }
}
