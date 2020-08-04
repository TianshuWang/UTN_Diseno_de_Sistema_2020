package presupuesto.validator.validacion;

import egreso.ItemEgreso;
import egreso.egreso.Egreso;
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

    private Boolean verificarItems(Egreso egreso,Presupuesto presupuesto){
        List<ItemPresupuesto> auxP = presupuesto.getItemPresupuestoList();
        List<ItemEgreso> auxC = new ArrayList<>();
        egreso.getItemEgresos().forEach(i->auxC.add(i));

        if(auxC.size() != auxP.size()){
            return false;
        }
        else{
            for(ItemPresupuesto p:auxP){
                ItemEgreso aux = auxC.stream().filter(c->c.getCantidad()==p.getCantidad() && c.getProductoServicio().getNombre()==p.getProductoServicio().getNombre())
                                              .findAny().orElse(null);

                if(aux != null){
                    p.setEgreso(egreso);//asociar el itemPresupuesto a la compra
                    auxC.remove(aux);
                }
                else{
                    return false;
                }
            }
        }
        return true;
    }
}
