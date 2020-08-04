package usuario.usuario;

import exceptions.*;
import organizacion.Organizacion;
import organizacion.criterio.CriterioClasificacion;

import java.io.IOException;

public class Administrador implements Rol{
    @Override
    public void otorgarJerarquiaAcriterios(CriterioClasificacion padre, CriterioClasificacion hijo){
        padre.agregarCriteriosHijos(hijo);
    }

    @Override
    public Usuario crearUsuario(String username, String password, Organizacion organizacion, Rol rol) throws UserException, IOException, PassawordException {
        return UsuarioFactory.crearUsuario(username,password,organizacion,rol);
    }
}
