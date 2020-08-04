package bandejaDeMensaje.criterioFiltracion;

import bandejaDeMensaje.mensaje.Mensaje;

public interface CriterioFiltracion {
    public boolean validarCriterio(Mensaje mensaje);
}
