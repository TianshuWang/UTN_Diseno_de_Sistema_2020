package direccion_moneda.mercadolibre_api.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "provincia")
public class Provincia {
    @Id
    @Expose(serialize = true, deserialize = true)
    public String id;

    @Column(name = "name")
    @Expose(serialize = true, deserialize = true)
    public String name;

    @ManyToOne
    @JoinColumn(name = "pais_id", referencedColumnName = "id")
    @Expose(serialize = true, deserialize = true)
    public Pais country;

    @Transient
    //@OneToMany(mappedBy = "state", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Ciudad[] cities;

    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Expose(serialize = true, deserialize = true)
    private List<Ciudad> ciudades;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Pais getCountry() {
        return country;
    }

    public Ciudad[] getCities() {
        return cities;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCountry(Pais country) {
        this.country = country;
    }

    public void setCities(Ciudad[] cities) {
        this.cities = cities;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
    public String getName() {
        return name;
    }
}
