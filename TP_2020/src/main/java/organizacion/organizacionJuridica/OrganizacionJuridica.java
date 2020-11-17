package organizacion.organizacionJuridica;

import direccion_moneda.DireccionPostal;
import organizacion.Organizacion;
import organizacion.OrganizacionBase;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@DiscriminatorValue("organizacionJuridica")
public class OrganizacionJuridica extends Organizacion {
    @Column(name = "cuit")
    private String cuit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private DireccionPostal direccionPostal;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "nro_IGJ")
    private String nroIGJ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_organizacion_juridica_id", referencedColumnName = "id")
    private TipoOrganizacionJuridica tipoOrganizacionJuridica;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrganizacionBase> basesList;

    public OrganizacionJuridica(){
        super();
        basesList = new ArrayList<>();
    }

    public void cargarBase(OrganizacionBase ...bases){
        Collections.addAll(basesList,bases);
    }

    //getters and setters

    public void setDireccionPostal(DireccionPostal direccionPostal) throws IOException {
        this.direccionPostal = direccionPostal;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setNroIGJ(String nroIGJ) {
        this.nroIGJ = nroIGJ;
    }

    public void setBasesList(List<OrganizacionBase> basesList) {
        this.basesList = basesList;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public void setTipoOrganizacionJuridica(TipoOrganizacionJuridica tipoOrganizacionJuridica) {
        this.tipoOrganizacionJuridica = tipoOrganizacionJuridica;
    }

    @Override
    public DireccionPostal getDireccionPostal() {
        return direccionPostal;
    }

    @Override
    public String getTipo() {
        return this.tipoOrganizacionJuridica.getTipo();
    }

    @Override
    public String getRazonSocial() {
        return this.razonSocial;
    }

    @Override
    public String getCuit() {
        return this.cuit;
    }

    @Override
    public String getSector() {
        return this.tipoOrganizacionJuridica.getSector();
    }

    @Override
    public String getMision() {
        return this.tipoOrganizacionJuridica.getMision();
    }

    @Override
    public String getCategoria() {
        return this.tipoOrganizacionJuridica.getCategoria();
    }

    @Override
    public OrganizacionJuridica getOrganizacionJuridica() {
        return null;
    }

    @Override
    public TipoOrganizacionJuridica getTipoOrganizacionJuridica() {
        return this.tipoOrganizacionJuridica;
    }

}
