package organizacion.criterio;

import entityPersistente.EntidadPersistente;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import organizacion.Organizacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "criterioClasificacion")
public class CriterioClasificacion extends EntidadPersistente {
    @Column(name = "descripcioon")
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;

    @OneToMany(mappedBy = "criterioClasificacion",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<CategoriaClasificacion> categoriaClasificacionList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<CriterioClasificacion> criteriosHijos;

    private CriterioClasificacion(){

    }

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

    public List<CriterioClasificacion> getCriteriosHijos() {
        return criteriosHijos;
    }
}
