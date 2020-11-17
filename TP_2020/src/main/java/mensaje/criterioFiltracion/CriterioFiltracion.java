package mensaje.criterioFiltracion;

import mensaje.mensaje.Mensaje;

public interface CriterioFiltracion {
    public boolean validarCriterio(Mensaje mensaje);
}
