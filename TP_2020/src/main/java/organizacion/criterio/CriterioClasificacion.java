package organizacion.criterio;

import organizacion.Organizacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CriterioClasificacion {
    private String descripcion;
    private Organizacion organizacion;
    private List<CategoriaClasificacion> categoriaClasificacionList;
    private List<CriterioClasificacion> criteriosHijos;

    public CriterioClasificacion(String descripcion,Organizacion organizacion){
        this.descripcion = descripcion;
        this.organizacion = organizacion;
        this.categoriaClasificacionList = new ArrayList<>();
        this.criteriosHijos = new ArrayList<>();
    }

    public void agregarCategorias(CategoriaClasificacion ...categoria){
        Collections.addAll(categoriaClasificacionList,categoria);
    }

    public void agregarCriteriosHijos(CriterioClasificacion ...criterios){
        Collections.addAll(criteriosHijos,criterios);
    }

    public void quitarCategoria(CategoriaClasificacion categoriaClasificacion){
        categoriaClasificacionList.remove(categoriaClasificacion);
    }

    //getters
    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<CategoriaClasificacion> getCategoriaClasificacionList() {
        return categoriaClasificacionList;
    }
}
