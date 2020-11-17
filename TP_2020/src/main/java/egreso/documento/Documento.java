package egreso.documento;

import converters.LocalDateAttributeConverter;
import egreso.Egreso;
import egreso.Proveedor;
import entityPersistente.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "documento")
public class Documento extends EntidadPersistente {
    //@Column(name = "identificador")
    //private String identificador;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "egreso_id", referencedColumnName = "id")
    private Egreso egreso;

    @Column(name = "fecha_emision")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaEmision;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_documento_id", referencedColumnName = "id")
    private TipoDeDocumento tipoDeDocumento;//comprobante link

    private Documento(){

    }

    public Documento(Proveedor proveedor,TipoDeDocumento tipoDeDocumento, LocalDate fechaEmision){
        this.proveedor = proveedor;
        this.fechaEmision = fechaEmision;
        this.tipoDeDocumento = tipoDeDocumento;
        //this.identificador = identificador;
    }
    

    public void cambiarTipoDeDocumento(TipoDeDocumento tipoDeDocumento){
        this.tipoDeDocumento = tipoDeDocumento;
    }

    //public String getIdentificador() {
        //return identificador;
    //}

    public void setEgreso(Egreso egreso) {
        this.egreso = egreso;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Egreso getEgreso() {
        return egreso;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public TipoDeDocumento getTipoDeDocumento() {
        return tipoDeDocumento;
    }


}
