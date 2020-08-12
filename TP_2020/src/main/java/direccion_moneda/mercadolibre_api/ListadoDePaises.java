package direccion_moneda.mercadolibre_api;

import direccion_moneda.mercadolibre_api.molde.Pais;
import direccion_moneda.mercadolibre_api.services.ServicioMercadolibre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListadoDePaises {
    private static ListadoDePaises instancia = null;
    private List<Pais> paises;

    private ListadoDePaises() throws IOException {
        this.paises = new ArrayList<>();
        this.paises = Arrays.asList(ServicioMercadolibre.instancia().paises());
    }

    public static ListadoDePaises getInstancia() throws IOException {
        if(instancia == null){
            instancia = new ListadoDePaises();
        }
        return instancia;
    }

    public Pais paisDeNombre(String nombre) throws IOException, ExcepcionMercadoLibreApi {
        Pais aux = this.paises.stream().filter(p -> p.name.equalsIgnoreCase(nombre)).findAny().orElse(null);
        if(aux == null){
            throw new ExcepcionMercadoLibreApi("No existe el pais");
        }
        return ServicioMercadolibre.instancia().pais(aux.id);
    }

    //getters
    public List<Pais> getPaises() {
        return paises;
    }
}
