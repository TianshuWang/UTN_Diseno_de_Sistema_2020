package egreso.documento;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("comprobante")
public class Comprobante extends TipoDeDocumento {

    @Column(name = "ruta")
    private String rutaArchivo;

    @Override
    public void operar() {
    }

    public void setRutaArchivo(String ruta) {
        this.rutaArchivo = ruta;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    @Override
    public String getLink() {
        return null;
    }

    @Override
    public String getTipo() {
        return "comprobante";
    }
}
