package direccion_moneda.mercadolibre_api.services;

import direccion_moneda.mercadolibre_api.model.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface MercadolibreService {

    @GET("countries")
    Call<Pais[]> paises();

    @GET("countries/{id}")
    Call<Pais> pais(@Path("id") String id);

    @GET("states/{id}")
    Call<Provincia> provincia(@Path("id") String id);

    @GET("cities/{id}")
    Call<Ciudad> ciudad(@Path("id") String id);

    @GET("currencies/{id}")
    Call<Moneda> moneda(@Path("id") String id);
}
