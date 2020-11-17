package mensaje.mensaje;

import entityPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "tipoMensaje")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class TipoDeMensaje extends EntidadPersistente {
    public abstract void operar();
}
