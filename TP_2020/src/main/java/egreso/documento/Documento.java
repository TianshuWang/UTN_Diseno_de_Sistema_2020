package egreso.documento;

import egreso.egreso.Egreso;
import egreso.proveedor.Proveedor;

import java.time.LocalDateTime;

public class Documento {
    private Proveedor proveedor;
    private Egreso egreso;
    private LocalDateTime fechaEmision;
    private TipoDeDocumento tipoDeDocumento;//comprobante link
    private String identificador;

    public Documento(Proveedor proveedor,TipoDeDocumento tipoDeDocumento,String identificador){
        this.proveedor = proveedor;
        this.fechaEmision = LocalDateTime.now();
        this.tipoDeDocumento = tipoDeDocumento;
        this.identificador = identificador;
    }

    public void showDocumento(){
        this.tipoDeDocumento.showContenido();
    }

    public void cambiarTipoDeDocumento(TipoDeDocumento tipoDeDocumento){
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setEgreso(Egreso egreso) {
        this.egreso = egreso;
    }
}
