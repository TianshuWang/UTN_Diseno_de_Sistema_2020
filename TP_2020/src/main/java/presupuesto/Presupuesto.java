package presupuesto;

import Importe.Importe;
import direccion_moneda.ConversorDeMoneda;
import direccion_moneda.mercadolibre_api.molde.Moneda;
import direccion_moneda.mercadolibre_api.services.ServicioMercadolibre;
import egreso.egreso.Egreso;
import generadorIdentificador.GeneradorIdentificador;
import organizacion.criterio.CategoriaClasificacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Presupuesto {
    private String identificador;
    private Egreso egreso;
    private Importe montoTotal;
    private List<ItemPresupuesto> itemPresupuestoList;
    private List<CategoriaClasificacion> categoriasList;

    public Presupuesto(Egreso egreso, ItemPresupuesto ...items) throws IOException {
        this.egreso = egreso;
        this.identificador = GeneradorIdentificador.generarIdentificadorPresupuesto("%05d");
        this.itemPresupuestoList = new ArrayList<>();
        Collections.addAll(itemPresupuestoList,items);
        Moneda monedaEgreso = this.egreso.getProveedor().getDireccionPostal().getUbicacion().getMoneda();
        for(ItemPresupuesto i:itemPresupuestoList){
            ConversorDeMoneda.instancia().convertir(i.getMontoTotal(),monedaEgreso);
        }
        calcularMontoTotal();
        this.categoriasList = new ArrayList<>();
    }

    private void calcularMontoTotal(){
        BigDecimal valorTotal = itemPresupuestoList.stream().map(i->i.getMontoTotal().getValor()).reduce(BigDecimal.ZERO,BigDecimal::add);
        Moneda monedaEgreso = this.egreso.getProveedor().getDireccionPostal().getUbicacion().getMoneda();
        this.montoTotal = new Importe(valorTotal.toString(),monedaEgreso);
    }

    public void agregarCategoriasClasificacion(CategoriaClasificacion...categoriaClasificacions){
        Collections.addAll(categoriasList,categoriaClasificacions);
    }

    //getters
    public Importe getMontoTotal() {
        return this.montoTotal;
    }

    public String getIdentificador() {
        return identificador;
    }

    public Egreso getEgreso() {
        return egreso;
    }

    public List<ItemPresupuesto> getItemPresupuestoList() {
        return itemPresupuestoList;
    }
}
