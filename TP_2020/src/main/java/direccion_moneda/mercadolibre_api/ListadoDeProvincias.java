package direccion_moneda.mercadolibre_api;

import direccion_moneda.mercadolibre_api.molde.Provincia;
import direccion_moneda.mercadolibre_api.services.ServicioMercadolibre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListadoDeProvincias {
    private static ListadoDeProvincias instancia = null;
    private List<Provincia> provincias;

    private ListadoDeProvincias(){
        this.provincias = new ArrayList<>();
    }

    public static ListadoDeProvincias getInstancia(){
        if(instancia == null){
            instancia = new ListadoDeProvincias();
        }
        return instancia;
    }

    public Provincia provinciaDeNombre(String pais, String nombre) throws IOException, ExcepcionMercadoLibreApi {
        this.provincias = Arrays.asList(ListadoDePaises.getInstancia().paisDeNombre(pais).states);
        Provincia aux = this.provincias.stream().filter(p -> p.name.equalsIgnoreCase(nombre)).findAny().orElse(null);
        if(aux == null){
            throw new ExcepcionMercadoLibreApi("No existe la provincia");
        }
        return ServicioMercadolibre.instancia().provincia(aux.id);
    }

    //getters
    public List<Provincia> getProvincias() {
        return provincias;
    }
}
