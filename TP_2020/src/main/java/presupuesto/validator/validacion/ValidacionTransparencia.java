package presupuesto.validator.validacion;

import egreso.Egreso;

import java.io.FileNotFoundException;

public interface ValidacionTransparencia {
    public Boolean cumplirValidacion(Egreso egreso) throws FileNotFoundException;
}
