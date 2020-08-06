package egreso.egreso.builder;
import Importe.Importe;
import direccion_moneda.ConversorDeMoneda;
import direccion_moneda.mercadolibre_api.molde.Moneda;
import egreso.ItemEgreso;
import egreso.MedioDePago;
import egreso.egreso.Egreso;
import egreso.proveedor.Proveedor;
import egreso.documento.Documento;
import organizacion.Organizacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class EgresoBuilder {
    private Egreso egreso;

    public EgresoBuilder(){
        this.egreso = new Egreso();
    }

    public EgresoBuilder agregarOrganizacion(Organizacion organizacion) {
        this.egreso.setOrganizacion(organizacion);
        return this;
    }

    public EgresoBuilder agregarProveedor(Proveedor proveedor) {
        this.egreso.setProveedor(proveedor);
        return this;
    }

    public EgresoBuilder agregarMedioDePago(MedioDePago medioDePago) {
        this.egreso.setMedioDePago(medioDePago);
        return this;
    }

    public EgresoBuilder agregarFechaDeOperacion(LocalDate fechaDeOperacion) {
        this.egreso.setFechaDeOperacion(fechaDeOperacion);
        return this;
    }

    public EgresoBuilder agregarDocumento(Documento documento) {
        if(documento != null){
            this.egreso.setDocumento(documento);
        }
        return this;
    }

    public EgresoBuilder agregarItemsEgreso(ItemEgreso ...itemsEgreso) throws IOException {
        List<ItemEgreso> itemsList = new ArrayList<>();
        Moneda moneda = this.egreso.getOrganizacion().getTipoDeOrganizacion().getDireccionPostal().getUbicacion().getMoneda();
        Collections.addAll(itemsList,itemsEgreso);
        for(ItemEgreso i:itemsList){
            ConversorDeMoneda.instancia().convertir(i.getMontoTtotal(),moneda);
        }
        this.egreso.setItemEgresos(itemsList);
        BigDecimal valorTotal = itemsList.stream().map(i->i.getMontoTtotal().getValor()).reduce(BigDecimal.ZERO,BigDecimal::add);
        this.egreso.setMontoTotal(new Importe(valorTotal.toString(),moneda));
        return this;
    }

    public Egreso build() throws ExcepcionDeCreacionDeEgreso {
        if(this.esNull(this.egreso::getOrganizacion)){
            throw new ExcepcionDeCreacionDeEgreso("No se especificó la organizacion");
        }

        if(this.esNull(this.egreso::getItemEgresos)){
            throw new ExcepcionDeCreacionDeEgreso("No se asignó un item");
        }

        if(this.esNull(this.egreso::getProveedor)){
            throw new ExcepcionDeCreacionDeEgreso("No se asignó el proveedor");
        }

        if(this.esNull(this.egreso::getFechaDeOperacion)){
            throw new ExcepcionDeCreacionDeEgreso("No se asignó la fecha de operacion");
        }

        if(this.esNull(this.egreso::getMedioDePago)){
            throw new ExcepcionDeCreacionDeEgreso("No se asignó el medio de pago");
        }

        return this.egreso;
    }

    private Boolean esNull(Supplier<Object> callback) {
        return callback.get() == null;
    }
}
