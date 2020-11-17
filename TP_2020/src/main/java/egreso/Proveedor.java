package egreso;

import direccion_moneda.DireccionPostal;
import entityPersistente.EntidadPersistente;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "proveedor")
public class Proveedor extends EntidadPersistente {
    @Column(name = "cuit")
    private String cuit;

    @Column(name = "nombre")
    private String nombre;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private DireccionPostal direccionPostal;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductoServicio> productoServicioList;

    private Proveedor(){

    }

    public Proveedor(Builder builder){
        this.cuit = builder.cuit;
        this.nombre = builder.nombre;
        this.direccionPostal = builder.direccionPostal;
        this.productoServicioList = builder.productoServicioList;
    }

    public static class Builder{
        private String cuit;
        private String nombre;
        private DireccionPostal direccionPostal;
        private List<ProductoServicio> productoServicioList;

        public Builder(){
            this.productoServicioList =new ArrayList<>();
        }

        public Builder agregarCuit(String cuit){
            this.cuit = cuit;
            return this;
        }

        public Builder agregarNombre(String nombre){
            this.nombre = nombre;
            return this;
        }

        public Builder agregarDireccionPostal(DireccionPostal direccionPostal){
            this.direccionPostal = direccionPostal;
            return this;
        }

        public Builder agregarProductos(ProductoServicio ...productos){
            Collections.addAll(this.productoServicioList,productos);
            return this;
        }

        public Proveedor build(){
            return new Proveedor(this);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getCuit() {
        return cuit;
    }
}
