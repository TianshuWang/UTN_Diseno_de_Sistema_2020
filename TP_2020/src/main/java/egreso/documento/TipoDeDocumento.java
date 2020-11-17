package egreso.documento;

import entityPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "tipodocumento")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class TipoDeDocumento extends EntidadPersistente {
    public abstract void operar();

    public abstract String getRutaArchivo();

    public abstract String getLink();

    public abstract String getTipo();
}
