package direccion_moneda.mercadolibre_api.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "ciudad")
public class Ciudad {
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Provincia getState() {
        return state;
    }

    public Pais getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Expose(serialize = true, deserialize = true)
    public String id;

    @Column(name = "name")
    @Expose(serialize = true, deserialize = true)
    public String name;

    @ManyToOne
    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
    public Provincia state;

    @ManyToOne
    @JoinColumn(name = "pais_id", referencedColumnName = "id")
    public Pais country;

    public void setState(Provincia state) {
        this.state = state;
    }

    public void setCountry(Pais country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }
}
