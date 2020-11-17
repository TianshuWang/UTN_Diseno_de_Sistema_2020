package presupuesto.criterioSeleccion;

import egreso.Egreso;
import presupuesto.Presupuesto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Comparator;


public class CriterioMenorCosto implements CriterioSeleccion {
    @Override
    public boolean validarCriterio(Egreso egreso) {
        Presupuesto menor = presupuestoMenorCosto(egreso);
        Boolean res = false;
        if(menor != null){
            res = (egreso.getPresupuestoSeleccionado().getId() == menor.getId());
        }
        return res;
    }

    private Presupuesto presupuestoMenorCosto(Egreso egreso){
        return egreso.getPresupuestosRequeridos().stream().min(Comparator.comparing(p->p.getMontoTotal())).orElse(null);
    }

    @Override
    public Presupuesto seleccionarPresupuesto(Egreso egreso){
        return presupuestoMenorCosto(egreso);
    }
}
