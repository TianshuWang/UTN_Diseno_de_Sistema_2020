package bandejaDeMensaje.criterioFiltracion;

import bandejaDeMensaje.mensaje.Mensaje;

public class FiltracionLeido implements CriterioFiltracion {
    private Boolean leido;

    public FiltracionLeido(Boolean leido){
        this.leido = leido;
    }

    @Override
    public boolean validarCriterio(Mensaje mensaje) {
        return mensaje.getLeido() == leido;
    }
}
