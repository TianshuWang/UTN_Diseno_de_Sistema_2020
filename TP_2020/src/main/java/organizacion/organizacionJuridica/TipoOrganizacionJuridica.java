package organizacion.organizacionJuridica;

import entityPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "tipoOrganizacionJuridica")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class TipoOrganizacionJuridica extends EntidadPersistente {

    public abstract String getSector();

    public abstract String getCategoria();

    public abstract String getMision();

    public abstract String getTipo();
}
