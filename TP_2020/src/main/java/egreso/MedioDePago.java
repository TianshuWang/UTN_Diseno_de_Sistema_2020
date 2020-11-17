package egreso;

import entityPersistente.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medioDePago")
public class MedioDePago extends EntidadPersistente {
    @Column(name = "identificador")
    private String identificador;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    private MedioDePago(){

    }

    public MedioDePago(String nombre,String identificador, String tipo){
        this.identificador = identificador;
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
