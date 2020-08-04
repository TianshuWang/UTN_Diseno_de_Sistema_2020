package organizacion.criterio;
import organizacion.criterio.CriterioClasificacion;

public class CategoriaClasificacion {
    private String descripcion;
    private CriterioClasificacion criterioClasificacion;

    public CategoriaClasificacion(String descripcion,CriterioClasificacion criterioClasificacion){
        this.descripcion = descripcion;
        this.criterioClasificacion = criterioClasificacion;
        criterioClasificacion.agregarCategorias(this);
    }

    //getters
    public String getDescripcion() {
        return descripcion;
    }

    public CriterioClasificacion getCriterioClasificacion() {
        return criterioClasificacion;
    }
}
