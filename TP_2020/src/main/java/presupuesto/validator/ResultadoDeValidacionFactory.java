package presupuesto.validator;
import bandejaDeMensaje.mensaje.Mensaje;
import bandejaDeMensaje.mensaje.Validacion;
import egreso.egreso.Egreso;
import presupuesto.validator.validacion.ValidacionTransparencia;

import java.io.FileNotFoundException;

public class ResultadoDeValidacionFactory {
    public static Mensaje getResultadoDeValidacion(Egreso c) throws FileNotFoundException {
        if(c == null){
            return null;
        }

        if(new ValidatorDeTransparencia().cumplirValidacion(c)){
            return new Mensaje("Validacion Pasada",new Validacion(c));
        }
        else{
            String validacionesNoCumplidas = "";
            for(ValidacionTransparencia v:new ValidatorDeTransparencia().validacionesNoCumplidas(c)){
                validacionesNoCumplidas += (v.getClass().getSimpleName()+" ");
            }
            return new Mensaje("Validacion No Pasada Por No Ha Cumplido Las Validaciones："+validacionesNoCumplidas,new Validacion(c));
        }
    }
}
