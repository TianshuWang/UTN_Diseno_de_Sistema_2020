package presupuesto.validator.validacion;

import egreso.ItemEgreso;
import egreso.Egreso;
import presupuesto.ItemPresupuesto;
import presupuesto.Presupuesto;

import java.util.ArrayList;
import java.util.List;

public class PresupuestoPresenteEnCompra implements ValidacionTransparencia {
    @Override
    public Boolean cumplirValidacion(Egreso egreso)  {
        return verificarPresupuesto(egreso);
    }

    private Boolean verificarPresupuesto(Egreso egreso){
        return egreso.getPresupuestosRequeridos().stream().anyMatch(p->verificarItems(egreso,p));
    }

    private Boolean verificarItem(ItemEgreso itemEgreso,ItemPresupuesto itemPresupuesto){
        return itemEgreso.getCantidad() == itemPresupuesto.getCantidad() &&
                itemEgreso.getProductoServicio().getNombre() == itemPresupuesto.getProductoServicio().getNombre();
    }

    private Boolean verificarItems(Egreso egreso,Presupuesto presupuesto){
        List<ItemPresupuesto> auxP = presupuesto.getItemPresupuestoList();
        List<ItemEgreso> auxE = new ArrayList<>();
        egreso.getItemEgresos().forEach(i->auxE.add(i));

        if(auxE.size() != auxP.size()){
            return false;
        }

        for(ItemPresupuesto ip:auxP) {
            ItemEgreso auxIe = auxE.stream().filter(ie -> verificarItem(ie, ip)).findAny().orElse(null);

            if (auxIe == null) {
                return false;
            }
            auxE.remove(auxIe);
        }
        return true;
    }
}
