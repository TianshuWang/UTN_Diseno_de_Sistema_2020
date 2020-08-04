package organizacion;

import direccion_moneda.DireccionPostal;
import exceptions.OrganizacionException;
import organizacion.organizacionJuridicaBuilder.OrganizacionJuridica;

import java.util.List;

public class OrganizacionBase implements TipoDeOrganizacion{
    protected String nombre;
    private String descripcion;
    private OrganizacionJuridica organizacionJuridica;

    public OrganizacionBase(String nombre,String descripcion,OrganizacionJuridica organizacionJuridica) throws OrganizacionException {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.organizacionJuridica = organizacionJuridica;
        if(this.organizacionJuridica == null){
            throw new OrganizacionException();
        }
        organizacionJuridica.cargarBase(this);
    }

    //getters
    public String getNombre() {
        return nombre;
    }

    @Override
    public List<OrganizacionBase> getBasesList() {
        return null;
    }

    @Override
    public String getCategoria() {
        return null;
    }

    @Override
    public DireccionPostal getDireccionPostal() {
        return this.organizacionJuridica.getDireccionPostal();
    }
}
