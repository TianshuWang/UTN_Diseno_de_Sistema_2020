package organizacion;
import exceptions.CriterioException;
import organizacion.criterio.AdministradorCriterios;
import organizacion.criterio.CriterioClasificacion;
import organizacion.criterio.CategoriaClasificacion;

public class Organizacion {
    private TipoDeOrganizacion tipoDeOrganizacion;//empresa OSC base
    private AdministradorCriterios administradorCriterios;

    public Organizacion(TipoDeOrganizacion tipoDeOrganizacion){
        this.tipoDeOrganizacion = tipoDeOrganizacion;
        administradorCriterios = new AdministradorCriterios(this);
    }

    public CriterioClasificacion crearCriterioClasificacion(String descripcion){
        return administradorCriterios.crearCriterio(descripcion,this);
    }

    public CategoriaClasificacion crearCategoriaClasificacion(String descripcion, CriterioClasificacion criterio) throws CriterioException {
        return administradorCriterios.crearCategoria(descripcion,criterio);
    }

    //getters
    public String getNombre(){
        return tipoDeOrganizacion.getNombre();
    }

    public TipoDeOrganizacion getTipoDeOrganizacion() {
        return tipoDeOrganizacion;
    }
}
