package direccion_moneda;

import direccion_moneda.mercadolibre_api.ExcepcionMercadoLibreApi;
import direccion_moneda.mercadolibre_api.ListadoDeCiudades;
import direccion_moneda.mercadolibre_api.ListadoDePaises;
import direccion_moneda.mercadolibre_api.ListadoDeProvincias;
import direccion_moneda.mercadolibre_api.molde.*;
import direccion_moneda.mercadolibre_api.services.ServicioMercadolibre;

import java.io.IOException;

public class Ubicacion {
    private Ciudad ciudad;
    private Provincia provincia;
    private Pais pais;
    private Moneda moneda;

    public Ubicacion(String ciudad, String provincia, String pais) throws IOException, ExcepcionMercadoLibreApi {
        this.pais = ListadoDePaises.getInstancia().paisDeNombre(pais);
        this.moneda = ServicioMercadolibre.instancia().moneda(this.pais.currency_id);
        this.provincia = ListadoDeProvincias.getInstancia().provinciaDeNombre(pais,provincia);
        this.ciudad = ListadoDeCiudades.getInstancia().ciudadDeNombre(pais,provincia,ciudad);
    }

    //getters
    public Pais getPais() {
        return pais;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public Provincia getProvincia() {
        return provincia;
    }
}
