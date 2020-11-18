package APIAsociacion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.LocalDateAdapter;
import egreso.Egreso;
import exceptions.BusinessError;
import exceptions.TransactionException;
import ingreso.Ingreso;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import usuario.usuario.Usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListaEgresosIngresosJSON {

    private Repositorio<Ingreso> repoIngreso;
    private Repositorio<Egreso> repoEgreso;

    public ListaEgresosIngresosJSON(){
        this.repoIngreso = FactoryRepositorio.get(Ingreso.class);
        this.repoEgreso = FactoryRepositorio.get(Egreso.class);
    }

    public String jsonDatos(Usuario usuario) throws TransactionException {
        List<Egreso> egresos = repoEgreso.buscarTodos();
        List<Egreso> egresoList = egresos.stream().
                filter(e->e.getUsuario() !=null && e.getUsuario().getOrganizacion().getId() == usuario.getOrganizacion().getId() && e.getIngresoAsociado() == null).
                collect(Collectors.toList());
        if(egresoList.isEmpty()) throw new TransactionException(BusinessError.ASOCIACION_EXCEPTION,"No Existen Egresos Para Asociar");

        List<Ingreso> ingresos = repoIngreso.buscarTodos();
        List<Ingreso> ingresoList = ingresos.stream().
                filter(i->i.getUsuario().getOrganizacion().getId() == usuario.getOrganizacion().getId()).
                collect(Collectors.toList());
        if(ingresoList.isEmpty()) throw new TransactionException(BusinessError.ASOCIACION_EXCEPTION,"No Existen Ingresos Para Asociar");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class,new LocalDateAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Map<String,Object> datos = new HashMap<>();
        datos.put("Egresos",egresoList);
        datos.put("Ingresos",ingresoList);

        String jsonDatos = gson.toJson(datos);

        return jsonDatos;
    }
}