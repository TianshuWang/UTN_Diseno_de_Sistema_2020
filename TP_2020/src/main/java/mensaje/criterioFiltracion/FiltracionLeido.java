package mensaje.criterioFiltracion;

import mensaje.mensaje.Mensaje;

public class FiltracionLeido implements CriterioFiltracion {
    private Boolean leido;

    private FiltracionLeido(){

    }

    public FiltracionLeido(Boolean leido){
        this.leido = leido;
    }

    @Override
    public boolean validarCriterio(Mensaje mensaje) {
        return mensaje.getLeido() == leido;
    }
}
