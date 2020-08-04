package bandejaDeMensaje;

import bandejaDeMensaje.criterioFiltracion.CriterioFiltracion;
import bandejaDeMensaje.mensaje.Mensaje;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DAOMemoriaBandeja implements DAOBandeja {
    private List<Mensaje> bandeja = new ArrayList<>();

    @Override
    public void agregar(Mensaje mensaje) {
        bandeja.add(mensaje);
    }

    @Override
    public void eliminar(Mensaje mensaje) {
        bandeja.remove(mensaje);
    }

    @Override
    public List<Mensaje> filtrar(CriterioFiltracion criterioFiltracion) {
        return bandeja.stream().filter(m->criterioFiltracion.validarCriterio(m)).collect(Collectors.toList());
    }

    @Override
    public Mensaje buscar(String codigo) {
        return null;
    }

}
