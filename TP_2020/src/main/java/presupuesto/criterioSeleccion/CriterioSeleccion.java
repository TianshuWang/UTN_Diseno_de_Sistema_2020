package presupuesto.criterioSeleccion;

import egreso.Egreso;
import presupuesto.Presupuesto;

import javax.persistence.*;

public interface CriterioSeleccion {

    public boolean validarCriterio(Egreso egreso);

    public Presupuesto seleccionarPresupuesto(Egreso egreso);
}
