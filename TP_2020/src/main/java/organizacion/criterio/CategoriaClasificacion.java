package organizacion.criterio;
import entityPersistente.EntidadPersistente;
import organizacion.criterio.CriterioClasificacion;

import javax.persistence.*;

@Entity
@Table(name = "categoriaClasificacion")
public class CategoriaClasificacion extends EntidadPersistente {
    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "criterio_clasificacion_id")
    private CriterioClasificacion criterioClasificacion;

    private CategoriaClasificacion(){

    }

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
