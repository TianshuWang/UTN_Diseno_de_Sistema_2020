package organizacion.organizacionJuridica;

import converters.TipoDeActivitadConverter;
import organizacion.categoria.CalculoCategoriaAFIP;
import organizacion.categoria.TipoDeActividad;

import javax.persistence.*;
import java.io.FileNotFoundException;

@Entity
@DiscriminatorValue("empresa")
public class Empresa extends TipoOrganizacionJuridica {
    @Column(name = "cantidad_persona")
    private int cantidadDePersona;

    @Column(name = "venta_anual")
    private float ventaAnual;

    @Column(name = "categoria")
    private String categoria; //micro pequenia medianaTramo1 medianaTramo2

    @Column(name = "sector")
    private String sector; //construccion servicios comericio agropecuario industriaYmineria

    @Column(name = "tipo_actividad")
    @Convert(converter = TipoDeActivitadConverter.class)
    private TipoDeActividad tipoDeActividad;//comisionista o noComisionista

    private Empresa(){

    }

    public Empresa(int cantidadDePersona,long ventaAnual, String sector,TipoDeActividad tipoDeActividad) throws FileNotFoundException {
        this.cantidadDePersona = cantidadDePersona;
        this.ventaAnual = ventaAnual;
        this.sector = sector;
        this.tipoDeActividad = tipoDeActividad;
        categorizar();
    }

    public void categorizar() throws FileNotFoundException {
        CalculoCategoriaAFIP.calcularCategoria(this);
    }

    public float getVentaAnual() {
        return ventaAnual;
    }

    public int getCantidadDePersona() {
        return cantidadDePersona;
    }

    public String getSector() {
        return sector;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String getMision() {
        return null;
    }

    @Override
    public String getTipo() {
        return "Empresa";
    }

    public TipoDeActividad getTipoDeActividad() {
        return tipoDeActividad;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
