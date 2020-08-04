package usuario.usuario;

import exceptions.*;
import organizacion.Organizacion;
import organizacion.criterio.CriterioClasificacion;

import java.io.IOException;

public interface Rol {
    public void otorgarJerarquiaAcriterios(CriterioClasificacion padre, CriterioClasificacion hijo) throws RolException;
    public Usuario crearUsuario(String username, String password, Organizacion organizacion, Rol rol) throws UserException, IOException, RolException, PassawordException;
}
