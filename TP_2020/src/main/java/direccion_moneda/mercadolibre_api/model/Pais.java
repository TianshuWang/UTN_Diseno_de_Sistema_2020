package direccion_moneda.mercadolibre_api.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pais")
public class Pais {
    public String getId() {
        return id;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public Provincia[] getStates() {
        return states;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    @Id
    public String id;

    @Column(name = "nombre")
    public String name;

    @Column(name = "currency_id")
    public String currency_id;

    @Transient
    public Provincia[] states;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Provincia> provincias;

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public void setStates(Provincia[] states) {
        this.states = states;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }
}
