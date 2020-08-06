package ingreso;

import Importe.Importe;
import direccion_moneda.ConversorDeMoneda;
import direccion_moneda.mercadolibre_api.molde.Moneda;
import generadorIdentificador.GeneradorIdentificador;
import organizacion.Organizacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ingreso {
    private String identificador;
    private String descripcion;
    private List<ItemIngreso> itemIngresoList;
    private Importe montoTotal;
    private Organizacion organizacion;
    private LocalDate fechaDeOperacion;

    public Ingreso(Organizacion organizacion, String descripcion, LocalDate fechaDeOperacion, ItemIngreso ...itemIngreso) throws IOException {
        this.identificador = GeneradorIdentificador.generarIdentificadorIngreso("%05d");
        this.organizacion = organizacion;
        this.fechaDeOperacion = fechaDeOperacion;
        this.descripcion = descripcion;
        this.itemIngresoList = new ArrayList<>();
        Collections.addAll(itemIngresoList,itemIngreso);
        Moneda moneda = this.organizacion.getTipoDeOrganizacion().getDireccionPostal().getUbicacion().getMoneda();
        for(ItemIngreso i:this.itemIngresoList){
            ConversorDeMoneda.instancia().convertir(i.getMontoTtotal(),moneda);
        }
        this.montoTotal = new Importe(calcularValorTotal().toString(),moneda);
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

    public String getDescripcion() {
        return descripcion;
    }

    public List<ItemIngreso> getItemIngresoList() {
        return itemIngresoList;
    }

    public Importe getMontoTotal() {
        return montoTotal;
    }

    public LocalDate getFechaDeOperacion() {
        return fechaDeOperacion;
    }
}
