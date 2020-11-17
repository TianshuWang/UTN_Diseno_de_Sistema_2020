package organizacion.categoria;

import organizacion.organizacionJuridica.Empresa;

public interface TipoDeActividad{
    public void calcularCategoria(Empresa empresa, Sector sector);
}
