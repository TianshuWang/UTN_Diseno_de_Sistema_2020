package presupuesto.validator;
import mensaje.mensaje.Mensaje;
import mensaje.mensaje.Validacion;
import egreso.Egreso;
import presupuesto.validator.validacion.ValidacionTransparencia;

import java.io.FileNotFoundException;

public class ResultadoDeValidacionFactory {
    public static Mensaje getResultadoDeValidacion(Egreso c) throws FileNotFoundException {
        if(c == null){
            return null;
        }

        Validacion validacion = new Validacion();
        validacion.setEgreso(c);
        if(new ValidatorDeTransparencia().cumplirValidacion(c)){
            return new Mensaje("El Egreso del Dia:" + c.getFechaDeOperacion() + " con Validacion Pasada",validacion);
        }
        else{
            String validacionesNoCumplidas = "";
            for(ValidacionTransparencia v:new ValidatorDeTransparencia().validacionesNoCumplidas(c)){
                validacionesNoCumplidas += (v.getClass().getSimpleName()+" ");
            }
            return new Mensaje("El Egreso del Dia:" + c.getFechaDeOperacion() + " con Validacion No Pasada Por No Ha Cumplido: "+validacionesNoCumplidas,validacion);
        }
    }
}
