package usuario.usuario;

import exceptions.*;
import organizacion.Organizacion;
import organizacion.criterio.CriterioClasificacion;

import java.io.IOException;

public class Comun implements Rol{
    @Override
    public void otorgarJerarquiaAcriterios(CriterioClasificacion padre, CriterioClasificacion hijo) throws RolException{
        throw new RolException("No Tiene Rol Como Administrador");
    }

    @Override
    public Usuario crearUsuario(String username, String password, Organizacion organizacion, Rol rol) throws RolException {
        throw new RolException("No Tiene Rol Como Administrador");
    }
}
