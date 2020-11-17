package egreso;
import direccion_moneda.mercadolibre_api.model.Moneda;
import egreso.documento.Documento;
import usuario.usuario.Usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EgresoBuilder {
    private Egreso egreso;

    public EgresoBuilder(){
        this.egreso = new Egreso();
    }

    public EgresoBuilder agregarProveedor(Proveedor proveedor) {
        this.egreso.setProveedor(proveedor);
        return this;
    }

    public EgresoBuilder agregarRequierePresupuesto(boolean requiere) {
        this.egreso.setRequerirPresupuesto(requiere);
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
        this.egreso.setDocumento(documento);
        return this;
    }

    public EgresoBuilder agregarMoneda(Moneda moneda){
        this.egreso.setMoneda(moneda);
        return this;
    }

    public EgresoBuilder agregarId(int id){
        this.egreso.setId(id);
        return this;
    }

    public EgresoBuilder agregarItemsEgreso(List<ItemEgreso> itemsEgreso) throws IOException {
        List<ItemEgreso> itemsList = new ArrayList<>();
        //Collections.addAll(itemsList, itemsEgreso);
        itemsList.addAll(itemsEgreso);
        itemsList.forEach(i->i.setEgreso(this.egreso));
        this.egreso.setItemEgresos(itemsList);

        double valorTotal = itemsList.stream().mapToDouble(ItemEgreso::getMontoTotal).sum();
        this.egreso.setMontoTotal(valorTotal);

        return this;
    }

    public EgresoBuilder agregarMontoTotal(double monto) throws IOException {
        this.egreso.setMontoTotal(monto);
        return this;
    }

    public EgresoBuilder agregarUsuario(Usuario usuario){
        this.egreso.setUsuario(usuario);
        return this;
    }

    public Egreso build() {
        return this.egreso;
    }
}
