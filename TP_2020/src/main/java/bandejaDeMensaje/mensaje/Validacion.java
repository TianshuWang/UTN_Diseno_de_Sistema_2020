package bandejaDeMensaje.mensaje;

import egreso.egreso.Egreso;

public class Validacion implements TipoDeMensaje {
    private Egreso egreso;

    public Validacion(Egreso egreso){
        this.egreso = egreso;
    }

    @Override
    public void mostrarMensaje(){
        System.out.print("Egreso:"+ egreso.getIdentificador());
    }

    public Egreso getEgreso() {
        return egreso;
    }
}
