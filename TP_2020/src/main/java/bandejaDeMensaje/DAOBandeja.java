package bandejaDeMensaje;

import bandejaDeMensaje.criterioFiltracion.CriterioFiltracion;
import bandejaDeMensaje.mensaje.Mensaje;

import java.util.List;

public interface DAOBandeja{
    public void agregar(Mensaje m);
    public void eliminar(Mensaje m);
    public List<Mensaje> filtrar(CriterioFiltracion criterioFiltracion);
    public Mensaje buscar(String codigo);
}
