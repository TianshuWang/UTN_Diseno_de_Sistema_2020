package presupuesto.criterioSeleccion;

import egreso.egreso.Egreso;
import presupuesto.Presupuesto;

public interface CriterioSeleccion {
    public boolean validarCriterio(Egreso egreso);

    public Presupuesto seleccionarPresupuesto(Egreso egreso);
}
