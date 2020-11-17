package mensaje.mensaje;

import converters.LocalDateAttributeConverter;
import entityPersistente.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mensaje")
public class Mensaje extends EntidadPersistente {
    @Column(name = "fecha_emision")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaDeEmision;

    @Column(name = "fecha_lectura")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaDeLectura;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_mensaje", referencedColumnName = "id")
    private TipoDeMensaje tipoDeMensaje;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "leido")
    
    private Boolean leido;

    private Mensaje(){

    }

    public Mensaje(String contenido,TipoDeMensaje tipoDeMensaje){
        this.contenido = contenido;
        this.fechaDeEmision = LocalDate.now();
        this.tipoDeMensaje = tipoDeMensaje;
        this.leido = false;
    }

    //getters y setters
    public LocalDate getFechaDeEmision() {
        return fechaDeEmision;
    }

    public Boolean getLeido() {
        return leido;
    }

    public LocalDate getFechaDeLectura() {
        return fechaDeLectura;
    }

    public TipoDeMensaje getTipoDeMensaje() {
        return tipoDeMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setFechaDeLectura(LocalDate fechaDeLectura) {
        this.fechaDeLectura = fechaDeLectura;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public void setFechaDeEmision(LocalDate fechaDeEmision) {
        this.fechaDeEmision = fechaDeEmision;
    }

    public void setTipoDeMensaje(TipoDeMensaje tipoDeMensaje) {
        this.tipoDeMensaje = tipoDeMensaje;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
