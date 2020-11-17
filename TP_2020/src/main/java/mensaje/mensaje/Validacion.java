package mensaje.mensaje;

import egreso.Egreso;
import entityPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@DiscriminatorValue("validacion")
public class Validacion extends TipoDeMensaje {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "egreso_id", referencedColumnName = "id")
    private Egreso egreso;

    @Override
    public void operar() {

    }

    public Egreso getEgreso() {
        return this.egreso;
    }

    public void setEgreso(Egreso egreso) {
        this.egreso = egreso;
    }

}
