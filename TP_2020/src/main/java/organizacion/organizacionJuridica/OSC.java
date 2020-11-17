package organizacion.organizacionJuridica;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("osc")
public class OSC extends TipoOrganizacionJuridica {
    @Column(name = "mision")
    private String mision;

    private OSC(){

    }

    public OSC(String mision) {
        this.mision = mision;
    }

    @Override
    public String getSector() {
        return null;
    }

    @Override
    public String getCategoria() {
        return null;
    }

    @Override
    public String getMision() {
        return this.mision;
    }

    @Override
    public String getTipo() {
        return "OSC";
    }
}
