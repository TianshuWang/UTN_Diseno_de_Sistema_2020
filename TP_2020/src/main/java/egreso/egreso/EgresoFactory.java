package egreso.egreso;

import egreso.ItemEgreso;
import egreso.MedioDePago;
import egreso.documento.Documento;
import egreso.egreso.builder.EgresoBuilder;
import egreso.egreso.builder.ExcepcionDeCreacionDeEgreso;
import egreso.proveedor.Proveedor;
import exceptions.DocumentException;
import organizacion.Organizacion;
import persistencia.Repositorio;

import java.io.IOException;
import java.time.LocalDate;

public class EgresoFactory {
    private static Repositorio<Egreso> repositorio;

    public static Egreso crearEgreso(Organizacion organizacion,Proveedor proveedor, MedioDePago medioDePago, LocalDate fechaDeOperacion, Documento documento, ItemEgreso... itemEgresos) throws IOException, DocumentException, ExcepcionDeCreacionDeEgreso {
        Egreso egresoNuevo = new EgresoBuilder().agregarOrganizacion(organizacion)
                                                .agregarFechaDeOperacion(fechaDeOperacion)
                                                .agregarMedioDePago(medioDePago)
                                                .agregarProveedor(proveedor)
                                                .agregarItemsEgreso(itemEgresos)
                                                .agregarDocumento(documento)
                                                .build();
        repositorio.agregar(egresoNuevo);
        return egresoNuevo;
    }
    public static void setRepositorio(Repositorio<Egreso> repositorio) {
        EgresoFactory.repositorio = repositorio;
    }

    public static Repositorio<Egreso> getRepositorio() {
        return repositorio;
    }
}
