package direccion_moneda.mercadolibre_api.model;

import javax.persistence.*;

@Entity
@Table(name = "moneda")
public class Moneda {

    @Id
    public String id;

    @Column(name = "simbol")
    public String symbol;

    @Column(name = "descripcion")
    public String description;

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

}
