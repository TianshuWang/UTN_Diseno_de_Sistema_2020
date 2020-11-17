package presupuesto.validator.validacion;

import egreso.Egreso;
import presupuesto.GetCantidadPresupuestos;

import java.io.FileNotFoundException;

public class CantidadRequeridaPresupuesto implements ValidacionTransparencia {
    @Override
    public Boolean cumplirValidacion(Egreso egreso) throws FileNotFoundException {
        return GetCantidadPresupuestos.getCantidad() <= egreso.getPresupuestosRequeridos().size();
    }
}
