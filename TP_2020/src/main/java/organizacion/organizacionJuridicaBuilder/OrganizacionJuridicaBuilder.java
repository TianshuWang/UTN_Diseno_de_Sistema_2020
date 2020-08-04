package organizacion.organizacionJuridicaBuilder;

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
        if(this.esNull(this.organizacionJuridica::getDireccionPostal)){
            throw new ExcepcionDeCreacionDeOrgnizacionBuilder("No se asignó la direccion postal");
        }

        if(this.esNull(this.organizacionJuridica::getCuit)){
            throw new ExcepcionDeCreacionDeOrgnizacionBuilder("No se asignó el Nro. cuit");
        }

        if(this.esNull(this.organizacionJuridica::getRazonSocial)){
            throw new ExcepcionDeCreacionDeOrgnizacionBuilder("No se asignó el razon social");
        }

        if(this.esNull(this.organizacionJuridica::getTipoOrganizacionJuridica)){
            throw new ExcepcionDeCreacionDeOrgnizacionBuilder("No se asignó el tipo de organizacion juridica");
        }
        return this.organizacionJuridica;
    }

    private Boolean esNull(Supplier<Object> callback) {
        return callback.get() == null;
    }
}
