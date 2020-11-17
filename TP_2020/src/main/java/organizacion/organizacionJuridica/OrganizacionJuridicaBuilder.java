package organizacion.organizacionJuridica;

import direccion_moneda.DireccionPostal;
import organizacion.OrganizacionBase;

import java.io.IOException;
import java.util.function.Supplier;

public class OrganizacionJuridicaBuilder {
    private OrganizacionJuridica organizacionJuridica;

    public OrganizacionJuridicaBuilder(){
        this.organizacionJuridica = new OrganizacionJuridica();
    }

    public OrganizacionJuridicaBuilder agregarNombre(String nombre){
        this.organizacionJuridica.setNombre(nombre);
        return this;
    }

    public OrganizacionJuridicaBuilder agregarCuit(String cuit){
        this.organizacionJuridica.setCuit(cuit);
        return this;
    }

    public OrganizacionJuridicaBuilder agregarNroIGJ(String IGJ){
        this.organizacionJuridica.setNroIGJ(IGJ);
        return this;
    }

    public OrganizacionJuridicaBuilder agregarRazonSocial(String razonSocial){
        this.organizacionJuridica.setRazonSocial(razonSocial);
        return this;
    }

    public OrganizacionJuridicaBuilder agregarDireccionPostal(DireccionPostal direccionPostal) throws IOException {
        this.organizacionJuridica.setDireccionPostal(direccionPostal);
        return this;
    }

    public OrganizacionJuridicaBuilder agregarBases(OrganizacionBase...bases){
        this.organizacionJuridica.cargarBase(bases);
        return this;
    }

    public OrganizacionJuridicaBuilder agregarTipoDeOrganizacionJjuridica(TipoOrganizacionJuridica tipoDeOrganizacionJjuridica){
        this.organizacionJuridica.setTipoOrganizacionJuridica(tipoDeOrganizacionJjuridica);
        return this;
    }

    public OrganizacionJuridica build() throws ExcepcionDeCreacionDeOrgnizacionBuilder {
        return this.organizacionJuridica;
    }

    private Boolean esNull(Supplier<Object> callback) {
        return callback.get() == null;
    }
}
