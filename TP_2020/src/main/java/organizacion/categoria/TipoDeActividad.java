package organizacion.categoria;

import organizacion.organizacionJuridicaBuilder.Empresa;

public interface TipoDeActividad {
    void calcularCategoria(Empresa empresa, Sector sector);
}
