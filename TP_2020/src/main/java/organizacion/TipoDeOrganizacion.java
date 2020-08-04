package organizacion;

import direccion_moneda.DireccionPostal;

import java.util.List;

public interface TipoDeOrganizacion {
    public String getNombre();
    public List<OrganizacionBase> getBasesList();
    public String getCategoria();
    public DireccionPostal getDireccionPostal();
}
