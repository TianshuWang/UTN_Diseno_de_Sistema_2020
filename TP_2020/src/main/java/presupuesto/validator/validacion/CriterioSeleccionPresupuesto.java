package presupuesto.validator.validacion;

import egreso.Egreso;

public class CriterioSeleccionPresupuesto implements ValidacionTransparencia {
    @Override
    public Boolean cumplirValidacion(Egreso egreso) {
        return egreso.getCriterioSeleccion().validarCriterio(egreso);
    }
}
