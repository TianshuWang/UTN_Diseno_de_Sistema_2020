package ingreso;
import organizacion.Organizacion;
import persistencia.Repositorio;

import java.io.IOException;
import java.time.LocalDate;


public class IngresoFactory {
    private static Repositorio<Ingreso> repositorio;

    public static Ingreso crearIngreso(Organizacion organizacion, String descripcion, LocalDate fechaDeOperacion, ItemIngreso... itemIngresos) throws IOException{
        Ingreso ingreso = new Ingreso(organizacion,descripcion,fechaDeOperacion,itemIngresos);
        repositorio.agregar(ingreso);
        return ingreso;
    }

    public static void setRepositorio(Repositorio<Ingreso> repositorio) {
        IngresoFactory.repositorio = repositorio;
    }
}
