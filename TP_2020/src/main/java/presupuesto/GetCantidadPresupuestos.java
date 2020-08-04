package presupuesto;

import getDataConfig.GetDataConfig;

import java.io.FileNotFoundException;

public interface GetCantidadPresupuestos {
    public static Integer getCantidad() throws FileNotFoundException {
        return Integer.parseInt(GetDataConfig.getValue("cantidadPresupuestosRequeridos"));
    }
}
