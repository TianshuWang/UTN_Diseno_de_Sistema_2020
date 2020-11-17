package organizacion;

import direccion_moneda.DireccionPostal;
import organizacion.organizacionJuridica.OrganizacionJuridica;
import organizacion.organizacionJuridica.TipoOrganizacionJuridica;

import javax.persistence.*;

@Entity
@DiscriminatorValue("organizacionBase")
public class OrganizacionBase extends Organizacion{
    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "organizacion_juridica_id", referencedColumnName = "id")
    private OrganizacionJuridica organizacionJuridica;

    private OrganizacionBase(){

    }

    @Override
    public DireccionPostal getDireccionPostal() {
        return null;
    }

    @Override
    public String getTipo() {
        return "Base";
    }

    @Override
    public String getRazonSocial() {
        return null;
    }

    @Override
    public String getCuit() {
        return null;
    }

    @Override
    public String getSector() {
        return null;
    }

    @Override
    public String getMision() {
        return null;
    }

    @Override
    public String getCategoria() {
        return null;
    }

    @Override
    public OrganizacionJuridica getOrganizacionJuridica() {
        return this.organizacionJuridica;
    }

    @Override
    public TipoOrganizacionJuridica getTipoOrganizacionJuridica() {
        return null;
    }

    public OrganizacionBase(OrganizacionJuridica organizacionJuridica,String nombre) {
        super();
        this.organizacionJuridica = organizacionJuridica;
        organizacionJuridica.cargarBase(this);
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setOrganizacionJuridica(OrganizacionJuridica organizacionJuridica) {
        this.organizacionJuridica = organizacionJuridica;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
