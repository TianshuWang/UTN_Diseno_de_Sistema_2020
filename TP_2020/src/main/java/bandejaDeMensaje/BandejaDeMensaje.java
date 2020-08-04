package bandejaDeMensaje;

import bandejaDeMensaje.criterioFiltracion.CriterioFiltracion;
import bandejaDeMensaje.mensaje.Mensaje;

import java.util.List;

public class BandejaDeMensaje {
    private DAOBandeja daoBandeja;

    public BandejaDeMensaje(DAOBandeja daoBandeja){
        this.daoBandeja = daoBandeja;
    }
    public  void agregar(Mensaje mensaje){
        daoBandeja.agregar(mensaje);
    }

    public  void eliminar(Mensaje mensaje){
        daoBandeja.eliminar(mensaje);
    }

    public Mensaje buscar(String codigo){
        return (Mensaje) daoBandeja.buscar(codigo);
    }

    public List<Mensaje> filtrar(CriterioFiltracion criterioFiltracion){
        return daoBandeja.filtrar(criterioFiltracion);
    }
}
