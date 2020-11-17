package organizacion;
import direccion_moneda.DireccionPostal;
import entityPersistente.EntidadPersistente;
import organizacion.organizacionJuridica.OrganizacionJuridica;
import organizacion.organizacionJuridica.TipoOrganizacionJuridica;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Organizacion extends EntidadPersistente {
    @Column(name = "nombre")
    protected String nombre;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract DireccionPostal getDireccionPostal();

    public abstract String getTipo();

    public abstract String getRazonSocial();

    public abstract String getCuit();

    public abstract String getSector();

    public abstract String getMision();

    public abstract String getCategoria();

    public abstract OrganizacionJuridica getOrganizacionJuridica();

    public abstract TipoOrganizacionJuridica getTipoOrganizacionJuridica();

}
