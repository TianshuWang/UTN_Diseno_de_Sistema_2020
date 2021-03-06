package organizacion.categoria;

import organizacion.organizacionJuridica.Empresa;

import javax.persistence.Persistence;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalculoCategoriaAFIP {
    private static List<Sector> sectores;

    static {
        try {
            sectores = new ArrayList<>(Arrays.asList(new Sector("Construccion"),new Sector("Comercio"),new Sector("Servicios"),new Sector("Agropecuario"),new Sector("IndustriaYmineria")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void calcularCategoria(Empresa unaEmpresa) throws FileNotFoundException {
        Sector sector = sectores.stream().filter(s -> s.getNombreSector().equalsIgnoreCase(unaEmpresa.getSector())).findAny().orElse(null);
        GeneradorCategoria.generarCategoria(sector);
        unaEmpresa.getTipoDeActividad().calcularCategoria(unaEmpresa, sector);
    }

    public static List<Sector> getSectores() {
        return sectores;
    }

    public static void setSectores(Sector...sector) {
        Collections.addAll(sectores,sector);
    }
}
