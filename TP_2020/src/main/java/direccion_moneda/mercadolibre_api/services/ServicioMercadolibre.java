package direccion_moneda.mercadolibre_api.services;

import direccion_moneda.mercadolibre_api.model.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioMercadolibre {
    private static ServicioMercadolibre instancia = null;
    private Retrofit retrofit;

    private ServicioMercadolibre() {
        this.retrofit = new Retrofit.Builder()
                                    .baseUrl("https://api.mercadolibre.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
    }

    public static ServicioMercadolibre instancia(){
        if(instancia== null){
            instancia = new ServicioMercadolibre();
        }
        return instancia;
    }

    public Pais[] paises() throws IOException {
        MercadolibreService mercadolibreService = this.retrofit.create(MercadolibreService.class);
        Call<Pais[]> requestPaises = mercadolibreService.paises();
        Response<Pais[]> responsePaises = requestPaises.execute();
        Pais[] paises = responsePaises.body();
        return paises;
    }

    public Moneda moneda(String id) throws IOException {
        MercadolibreService mercadolibreService = this.retrofit.create(MercadolibreService.class);
        Call<Moneda> requestMoneda = mercadolibreService.moneda(id);
        Response<Moneda> responseMoneda = requestMoneda.execute();
        Moneda moneda = responseMoneda.body();
        return moneda;
    }

    public Pais pais(String id) throws IOException {
        MercadolibreService mercadolibreService = this.retrofit.create(MercadolibreService.class);
        Call<Pais> requestPais = mercadolibreService.pais(id);
        Response<Pais> responsePais = requestPais.execute();
        Pais pais = responsePais.body();
        return pais;
    }

    public Provincia provincia(String id) throws IOException {
        MercadolibreService mercadolibreService = this.retrofit.create(MercadolibreService.class);
        Call<Provincia> requestProvincia = mercadolibreService.provincia(id);
        Response<Provincia> responseProvincia = requestProvincia.execute();
        Provincia provincia = responseProvincia.body();
        return provincia;
    }

    public Ciudad ciudad(String id) throws IOException {
        MercadolibreService mercadolibreService = this.retrofit.create(MercadolibreService.class);
        Call<Ciudad> requestCiudad = mercadolibreService.ciudad(id);
        Response<Ciudad> responseCiudad = requestCiudad.execute();
        Ciudad ciudad = responseCiudad.body();
        return ciudad;
    }
}
