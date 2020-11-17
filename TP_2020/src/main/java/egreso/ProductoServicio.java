package egreso;

import direccion_moneda.mercadolibre_api.model.Moneda;
import entityPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "producto")
public class ProductoServicio extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo")
    private String tipoProductoServicio; //producto servicio

    private ProductoServicio(){

    }

    public ProductoServicio(String nombre, String descripcion,String tipoProductoServicio){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoProductoServicio = tipoProductoServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipoProductoServicio() {
        return tipoProductoServicio;
    }

}
