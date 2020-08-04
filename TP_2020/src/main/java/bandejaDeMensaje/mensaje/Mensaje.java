package bandejaDeMensaje.mensaje;

import java.time.LocalDate;

public class Mensaje {
    private LocalDate fechaDeEmision;
    private LocalDate fechaDeLectura;
    private TipoDeMensaje tipoDeMensaje;
    private String contenido;
    private Boolean leido;

    public Mensaje(String contenido,TipoDeMensaje tipoDeMensaje){
        this.contenido = contenido;
        this.fechaDeEmision = LocalDate.now();
        this.tipoDeMensaje = tipoDeMensaje;
        this.leido = false;
    }

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
}
