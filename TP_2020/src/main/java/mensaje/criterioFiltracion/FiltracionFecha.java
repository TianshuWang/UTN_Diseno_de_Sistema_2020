package mensaje.criterioFiltracion;

import mensaje.mensaje.Mensaje;

import java.time.LocalDate;

public class FiltracionFecha implements CriterioFiltracion {
    private LocalDate fecha;

    private FiltracionFecha(){

    }

    public FiltracionFecha(LocalDate fecha){
        this.fecha = fecha;
    }

    public boolean validarCriterio(Mensaje mensaje) {
        return mensaje.getFechaDeEmision().isEqual(fecha);
    }
}
