package organizacion.organizacionJuridicaBuilder;

import direccion_moneda.DireccionPostal;
import organizacion.OrganizacionBase;
import organizacion.TipoDeOrganizacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrganizacionJuridica implements TipoDeOrganizacion {
    protected String nombre;
    protected DireccionPostal direccionPostal;
    protected String razonSocial;
    protected String nroIGJ;
    protected String cuit;
    private TipoOrganizacionJuridica tipoOrganizacionJuridica;
    protected List<OrganizacionBase> basesList;

    public OrganizacionJuridica(){
        basesList = new ArrayList<>();
    }

    public void cargarBase(OrganizacionBase ...bases){
        Collections.addAll(basesList,bases);
    }

    //getters
    public String getNombre() {
        return nombre;
    }

    public List<OrganizacionBase> getBasesList() {
        return basesList;
    }

    @Override
    public String getCategoria() {
        return tipoOrganizacionJuridica.getCategoria();
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getNroIGJ() {
        return nroIGJ;
    }

    public String getCuit() {
        return cuit;
    }

    public TipoOrganizacionJuridica getTipoOrganizacionJuridica() {
        return tipoOrganizacionJuridica;
    }

    //setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccionPostal(DireccionPostal direccionPostal) throws IOException {
        this.direccionPostal = direccionPostal;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setNroIGJ(String nroIGJ) {
        this.nroIGJ = nroIGJ;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public void setBasesList(List<OrganizacionBase> basesList) {
        this.basesList = basesList;
    }

    public void setTipoOrganizacionJuridica(TipoOrganizacionJuridica tipoOrganizacionJuridica) {
        this.tipoOrganizacionJuridica = tipoOrganizacionJuridica;
    }

    public DireccionPostal getDireccionPostal() {
        return direccionPostal;
    }
}
