package organizacion.criterio;

import exceptions.CriterioException;
import organizacion.Organizacion;

import java.util.ArrayList;
import java.util.List;

public class AdministradorCriterios {
    private Organizacion organizacion;
    private List<CriterioClasificacion> criteriosOrganizacion;
    private List<CategoriaClasificacion> categoriasOrganizacion;

    public AdministradorCriterios(Organizacion organizacion){
        this.organizacion = organizacion;
        criteriosOrganizacion = new ArrayList<>();
        categoriasOrganizacion = new ArrayList<>();
    }

    public CriterioClasificacion crearCriterio(String descripcion, Organizacion organizacion){
        CriterioClasificacion criterioNuevo = new CriterioClasificacion(descripcion,organizacion);
        criteriosOrganizacion.add(criterioNuevo);
        return criterioNuevo;
    }

    public CategoriaClasificacion crearCategoria(String descripcion,CriterioClasificacion criterioClasificacion) throws CriterioException {
        if(!criterioClasificacion.getOrganizacion().equals(organizacion)){
            throw new CriterioException();
        }
        CategoriaClasificacion categoriaNueva = new CategoriaClasificacion(descripcion,criterioClasificacion);
        categoriasOrganizacion.add(categoriaNueva);
        return categoriaNueva;
    }

    public void eliminarCriterio(CriterioClasificacion criterio){
        criteriosOrganizacion.remove(criterio);
    }

    public void eliminarCategoria(CategoriaClasificacion categoriaClasificacion){
        categoriasOrganizacion.remove(categoriaClasificacion);
    }
}
