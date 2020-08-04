package organizacion.categoria;
import getDataConfig.GetDataConfig;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class GeneradorCategoria {
    public static void generarCategoria(Sector sector) throws FileNotFoundException {
        Long[] noComisionista = pasarLong(GetDataConfig.getValue(sector.getNombreSector().toLowerCase()).split("\\|"));
        Integer[] comisionista = pasarInteger(GetDataConfig.getValue(sector.getNombreSector().toLowerCase()+"_comisionista").split("\\|"));

        sector.agregarCategorias(
                new Categoria("Micro",noComisionista[0], comisionista[0], 1) ,
                new Categoria("Pequeña",noComisionista[1], comisionista[1], 2),
                new Categoria("Mediana Tramo 1",noComisionista[2], comisionista[2], 3),
                new Categoria("Mediana Tramo 2",noComisionista[3], comisionista[3], 4));
    }

    private static Long[] pasarLong(String... criterios){
        return Arrays.stream(criterios).map(Long::valueOf).toArray(Long[]::new);
    }

    private static Integer[] pasarInteger(String... criterios){
        return Arrays.stream(criterios).map(Integer::valueOf).toArray(Integer[]::new);
    }
}
