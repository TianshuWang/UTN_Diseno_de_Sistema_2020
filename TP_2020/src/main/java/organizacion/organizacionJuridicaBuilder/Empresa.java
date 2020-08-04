package organizacion.organizacionJuridicaBuilder;

import organizacion.categoria.CalculoCategoriaAFIP;
import organizacion.categoria.TipoDeActividad;
import organizacion.organizacionJuridicaBuilder.TipoOrganizacionJuridica;

import java.io.FileNotFoundException;

public class Empresa implements TipoOrganizacionJuridica {
    private int cantidadDePersona;
    private float ventaAnual;
    private String categoria; //micro pequenia medianaTramo1 medianaTramo2
    private String sector; //construccion servicios comericio agropecuario industriaYmineria
    private TipoDeActividad tipoDeActividad;//comisionista o noComisionista

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

    public TipoDeActividad getTipoDeActividad() {
        return tipoDeActividad;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


}
