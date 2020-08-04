package presupuesto.criterioSeleccion;

import egreso.egreso.Egreso;
import presupuesto.Presupuesto;

import java.util.Comparator;

public class CriterioMenorCosto implements CriterioSeleccion {
    @Override
    public boolean validarCriterio(Egreso egreso) {
        return egreso.getPresupuestoSeleccionado().equals(presupuestoMenorCosto(egreso));
    }

    private Presupuesto presupuestoMenorCosto(Egreso egreso){
        return egreso.getPresupuestosRequeridos().stream().min(Comparator.comparing(p->p.getMontoTotal().getValor())).get();
    }

    @Override
    public Presupuesto seleccionarPresupuesto(Egreso egreso){
        return presupuestoMenorCosto(egreso);
    }
}
