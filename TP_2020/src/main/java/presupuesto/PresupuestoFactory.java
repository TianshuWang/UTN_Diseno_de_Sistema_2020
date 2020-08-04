package presupuesto;

import direccion_moneda.mercadolibre_api.services.ServicioMercadolibre;
import egreso.egreso.Egreso;
import persistencia.Repositorio;

import java.io.IOException;

public class PresupuestoFactory {
    private static Repositorio<Presupuesto> repositorio;

    public static Presupuesto crearPresupuesto(Egreso egreso,ItemPresupuesto... items) throws IOException{
        Presupuesto presupuesto = new Presupuesto(egreso,items);
        repositorio.agregar(presupuesto);
        return presupuesto;
    }

    public static void setRepositorio(Repositorio<Presupuesto> repositorio) {
        PresupuestoFactory.repositorio = repositorio;
    }
}
