package direccion_moneda;

import direccion_moneda.mercadolibre_api.model.Ciudad;
import direccion_moneda.mercadolibre_api.model.Pais;
import direccion_moneda.mercadolibre_api.model.Provincia;
import entityPersistente.EntidadPersistente;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Table(name = "direccion")
public class DireccionPostal extends EntidadPersistente {
    @Column(name = "calle")
    private String calle;

    @Column(name = "altura")
    private Integer altura;

    @Column(name = "piso")
    private Integer piso;

    @Column(name = "nro")
    private Integer nro;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ciudad_id", referencedColumnName = "id")
    private Ciudad ciudad;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
    private Provincia provincia;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pais_id", referencedColumnName = "id")
    private Pais pais;

    private DireccionPostal(){}

    public DireccionPostal(Builder builder){
        this.calle = builder.calle;
        this.altura = builder.altura;
        this.piso = builder.piso;
        this.nro = builder.nro;
        this.pais = builder.pais;
        this.provincia = builder.provincia;
        this.ciudad = builder.ciudad;
    }

    public static class Builder{
        private String calle;
        private Integer altura;
        private Integer piso;
        private Integer nro;
        private Ciudad ciudad;
        private Provincia provincia;
        private Pais pais;

        public Builder agregarCalle(String calle){
            this.calle = calle;
            return this;
        }

        public Builder agregarAltura(int altura){
            this.altura = altura;
            return this;
        }

        public Builder agregarPiso(int piso){
            this.piso = piso;
            return this;
        }

        public Builder agregarNro(int nro){
            this.nro = nro;
            return this;
        }

        public Builder agregarCiudad(Ciudad ciudad) {
            this.ciudad = ciudad;
            return this;
        }

        public Builder agregarProvincia(Provincia provincia){
            this.provincia = provincia;
            return this;
        }

        public Builder agregarPais(Pais pais){
            this.pais = pais;
            return this;
        }

        public DireccionPostal build(){
            return new DireccionPostal(this);
        }
    }

    public String getCalle() {
        return calle;
    }

    public Integer getAltura() {
        return altura;
    }

    public Integer getPiso() {
        return piso;
    }

    public Integer getNro() {
        return nro;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public Pais getPais() {
        return pais;
    }
}
