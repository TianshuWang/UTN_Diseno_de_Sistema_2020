package presupuesto.validator;

import bandejaDeMensaje.mensaje.Mensaje;
import egreso.egreso.Egreso;
import exceptions.UserException;
import presupuesto.validator.validacion.CantidadRequeridaPresupuesto;
import presupuesto.validator.validacion.CriterioSeleccionPresupuesto;
import presupuesto.validator.validacion.PresupuestoPresenteEnCompra;
import presupuesto.validator.validacion.ValidacionTransparencia;
import usuario.usuario.Usuario;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ValidatorDeTransparencia {
    private List<ValidacionTransparencia> validacionList;

    public ValidatorDeTransparencia(){
       this.validacionList = new ArrayList<>();
       agregarValidaciones(new CantidadRequeridaPresupuesto(),new PresupuestoPresenteEnCompra(),new CriterioSeleccionPresupuesto());
    }

    public void agregarValidaciones(ValidacionTransparencia ...validaciones){
        Collections.addAll(validacionList,validaciones);
    }

    public Boolean cumplirValidacion(Egreso egreso) throws FileNotFoundException {
        for (ValidacionTransparencia v : validacionList) {
            return v.cumplirValidacion(egreso)?true:false;
        }
        return true;
    }

    public List<ValidacionTransparencia> validacionesNoCumplidas(Egreso egreso)throws FileNotFoundException{
        List<ValidacionTransparencia> list = new ArrayList<>();
        for (ValidacionTransparencia v : validacionList) {
            if (!v.cumplirValidacion(egreso)) {
                list.add(v);
            }
        }
        return list;
    }

    public void generarResultadoDeValidacion(Usuario user) throws UserException, FileNotFoundException {
        List<Egreso> comprasRequierenPresupuesto = user.getEgresoList().stream().filter(c->c.getCriterioSeleccion() != null).collect(Collectors.toList());
        List<Egreso> noValidados = comprasRequierenPresupuesto.stream().filter(c->c.getValidado().equals(false)).collect(Collectors.toList());
        for(Egreso c:noValidados){
            Mensaje mensajeValidacion = ResultadoDeValidacionFactory.getResultadoDeValidacion(c);
            user.getBandejaValidacion().agregar(mensajeValidacion);
            c.setValidado(true);
        }
    }
}
