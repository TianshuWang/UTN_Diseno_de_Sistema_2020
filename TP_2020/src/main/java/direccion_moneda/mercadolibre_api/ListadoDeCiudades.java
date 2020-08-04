package direccion_moneda.mercadolibre_api;

import direccion_moneda.mercadolibre_api.molde.Ciudad;
import direccion_moneda.mercadolibre_api.services.ServicioMercadolibre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListadoDeCiudades {
    private static ListadoDeCiudades instancia = null;
    private List<Ciudad> ciudades;

    private ListadoDeCiudades(){
        this.ciudades = new ArrayList<>();
    }

    public static ListadoDeCiudades getInstancia(){
        if(instancia == null){
            instancia = new ListadoDeCiudades();
        }
        return instancia;
    }

    public Ciudad ciudadDeNombre(String pais,String provincia,String nombre) throws IOException, ExcepcionMercadoLibreApi {
        this.ciudades = Arrays.asList(ListadoDeProvincias.getInstancia().provinciaDeNombre(pais,provincia).cities);
        Ciudad aux = this.ciudades.stream().filter(c -> c.name.equalsIgnoreCase(nombre)).findAny().orElse(null);
        if(aux == null){
            throw new ExcepcionMercadoLibreApi("No existe la ciudad");
        }
        return ServicioMercadolibre.instancia().ciudad(aux.id);
    }

    //getters
    public List<Ciudad> getCiudades() {
        return ciudades;
    }
}
