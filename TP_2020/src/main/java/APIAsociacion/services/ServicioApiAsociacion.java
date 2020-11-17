package APIAsociacion.services;

import APIAsociacion.ListaEgresosIngresosJSON;
import APIAsociacion.Operacion;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import direccion_moneda.mercadolibre_api.model.Moneda;
import egreso.Egreso;
import exceptions.BusinessError;
import exceptions.TransactionException;
import ingreso.Ingreso;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import usuario.usuario.Usuario;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioApiAsociacion {

    private static ServicioApiAsociacion instancia = null;
    private Retrofit retrofit;

    private ServicioApiAsociacion() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://augdan.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static ServicioApiAsociacion instancia() {
        if (instancia == null) {
            instancia = new ServicioApiAsociacion();
        }
        return instancia;
    }

    public Map<String, List<Operacion>> envioJsonPost(Usuario usuario, int unRangoFecha, String unCriterio, String unCriterioOrden) throws IOException, TransactionException {

        Map<String, List<Operacion>> listaResultados;
        Repositorio<Ingreso> repoIngreso = FactoryRepositorio.get(Ingreso.class);
        Repositorio<Egreso> repoEgreso = FactoryRepositorio.get(Egreso.class);

        Gson gson = new Gson();

        ApiService apiService = this.retrofit.create(ApiService.class);

        ListaEgresosIngresosJSON lista = new ListaEgresosIngresosJSON();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),lista.jsonDatos(usuario));

        try{
            Call<ResponseBody> requestLista = apiService.enviarEgresosIngresos(requestBody,unRangoFecha,unCriterio, unCriterioOrden);

            Response<ResponseBody> responseLista = requestLista.execute();

            ResponseBody listaResult = responseLista.body();

            Type listaAsociaciones =new TypeToken<Map<String, List<Operacion>>>(){}.getType();
            listaResultados = gson.fromJson(listaResult.string(), listaAsociaciones);

            for (Map.Entry<String, List<Operacion>> entry : listaResultados.entrySet()) {
                Ingreso ingreso = repoIngreso.buscar(Integer.parseInt(entry.getKey()));
                for(Operacion o:entry.getValue()){
                    Egreso egreso = repoEgreso.buscar(o.id);
                    egreso.setIngresoAsociado(ingreso);
                    repoEgreso.modificar(egreso);
                    ingreso.getEgresosAsociados().add(egreso);
                    repoIngreso.modificar(ingreso);
                }
            }
        }
        catch(Exception e){
            throw new TransactionException(BusinessError.ASOCIACION_EXCEPTION,
                    "El Servicio No Esta Disponbile En Este Momento, Por Favor Intente Más Tarde");
        }

        return listaResultados;
    }
}
