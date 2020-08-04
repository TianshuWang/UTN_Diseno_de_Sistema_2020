package exceptions;

import egreso.egreso.Egreso;
import organizacion.Organizacion;

public class EgresoException extends Exception{
    public EgresoException(){
        super("Egreso No Requiere Presupuesto");
    }

    public EgresoException(Organizacion organizacion){
        super("Egreso No Existe En La Organizacion "+organizacion.getNombre());
    }

    public EgresoException(Egreso egreso){
        super("No Se Puede Cargar Un Presupuesto Al Egreso:"+ egreso.getIdentificador());
    }
}
