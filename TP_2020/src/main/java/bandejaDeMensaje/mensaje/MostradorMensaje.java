package bandejaDeMensaje.mensaje;

import java.time.LocalDate;

public interface MostradorMensaje {
    static Boolean activo = true;

    static void mostrar(Mensaje mensaje){
        if(activo == true){
            mensaje.setFechaDeLectura(LocalDate.now());
            mensaje.getTipoDeMensaje().mostrarMensaje();
            mensaje.setLeido(true);
            System.out.println("|Mensaje:"+mensaje.getContenido()+"|Fecha De Emision:"+mensaje.getFechaDeEmision() + "|Fecha De Lectura:"+mensaje.getFechaDeLectura()+"|Leido:"+mensaje.getLeido());
        }
    }
}
