package ingreso;

import com.google.gson.annotations.Expose;
import converters.LocalDateAttributeConverter;
import direccion_moneda.mercadolibre_api.model.Moneda;
import egreso.Egreso;
import entityPersistente.EntidadPersistente;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import presupuesto.Presupuesto;
import usuario.usuario.Usuario;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingreso")
public class Ingreso extends EntidadPersistente {
    @Column(name = "descripcion")
    private String descripcion;

    @Expose(serialize = true, deserialize = true)
    @Column(name = "monto_total")
    private double montoTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "moneda_id", referencedColumnName = "id")
    private Moneda moneda;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "username")
    private Usuario usuario;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "fecha_operacion")
    @Expose(serialize = true, deserialize = true)
    private LocalDate fechaDeOperacion;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "fecha_aceptacion")
    private LocalDate fechaDeAceptacion;

    @OneToMany(mappedBy = "ingresoAsociado", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Egreso> egresosAsociados;

    private Ingreso(){

    }

    public Ingreso(Builder builder) {
        this.usuario = builder.usuario;
        this.fechaDeOperacion = builder.fechaDeOperacion;
        this.fechaDeAceptacion = builder.fechaDeAceptacion;
        this.descripcion = builder.descripcion;
        this.montoTotal = builder.montoTotal;
        this.moneda = builder.moneda;
        this.egresosAsociados = new ArrayList<>();
    }

    public static class Builder{
        private String descripcion;
        private double montoTotal;
        private Moneda moneda;
        private LocalDate fechaDeOperacion;
        private Usuario usuario;
        private LocalDate fechaDeAceptacion;

        public Builder agregarDescripcion(String descripcion){
            this.descripcion = descripcion;
            return this;
        }

        public Builder agregarMontoTotal(double montoTotal){
            this.montoTotal = montoTotal;
            return this;
        }

        public Builder agregarMoneda(Moneda moneda){
            this.moneda = moneda;
            return this;
        }

        public Builder agregarFechaDeOperacion(LocalDate fecha){
            this.fechaDeOperacion = fecha;
            return this;
        }

        public Builder agregarFechaDeAceptacion(LocalDate fecha){
            this.fechaDeAceptacion = fecha;
            return this;
        }

        public Builder agregarUsuario(Usuario usuario){
            this.usuario = usuario;
            return this;
        }

        public Ingreso build(){
            return new Ingreso(this);
        }
    }


    //getters and setters

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFechaDeOperacion() {
        return fechaDeOperacion;
    }

    public LocalDate getFechaDeAceptacion() {
        return fechaDeAceptacion;
    }

    public List<Egreso> getEgresosAsociados() {
        return egresosAsociados;
    }

    public void setEgresosAsociados(List<Egreso> egresosAsociados) {
        this.egresosAsociados = egresosAsociados;
    }
}
