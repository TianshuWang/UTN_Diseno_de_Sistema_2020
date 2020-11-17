package egreso.documento;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("link")
public class Link extends TipoDeDocumento{

    @Column(name = "link")
    private String link;

    @Override
    public void operar() {

    }

    @Override
    public String getRutaArchivo() {
        return null;
    }

    @Override
    public String getTipo() {
        return "link";
    }

    @Override
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
