package bandejaDeMensaje.criterioFiltracion;

import bandejaDeMensaje.mensaje.Mensaje;

import java.time.LocalDate;

public class FiltracionFecha implements CriterioFiltracion {
    private LocalDate fecha;

    public FiltracionFecha(LocalDate fecha){
        this.fecha = fecha;
    }

    public boolean validarCriterio(Mensaje mensaje) {
        return mensaje.getFechaDeEmision().isEqual(fecha);
    }
}
