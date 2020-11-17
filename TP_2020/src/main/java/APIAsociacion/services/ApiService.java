package APIAsociacion.services;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("link/")
    Call<ResponseBody> enviarEgresosIngresos(@Body RequestBody listas,
                                             @Query("rangoFecha") int rangoFecha,
                                             @Query("vincularPor") String criterioVinculacion,
                                             @Query("criterioOrden") String criterioDeOrden);


}