package tester;

import APIAsociacion.Operacion;
import APIAsociacion.services.ServicioApiAsociacion;
import exceptions.TransactionException;
import org.junit.Test;
import org.junit.*;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import usuario.usuario.Usuario;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Asociacion_Test {

    ServicioApiAsociacion servicioAsociacion = ServicioApiAsociacion.instancia();

    @Test
    public void test_egreso_ingreso() throws IOException, TransactionException {
        Repositorio<Usuario> repoUsuario = FactoryRepositorio.get(Usuario.class);
        Usuario usuario = repoUsuario.buscar("usuarioA@gmail.com");
        Map<String, List<Operacion>> resultadoAsociacion = servicioAsociacion.envioJsonPost(usuario,30,"egresos","montoTotal");

        List<Operacion> operacionesResultado = resultadoAsociacion.get("0");
        Operacion operacionResultado = operacionesResultado.get(0);

        Assert.assertEquals(1, operacionResultado.getId());
        Assert.assertEquals("13-10-2020", operacionResultado.getFechaDeOperacion());
        Assert.assertEquals(10.0, operacionResultado.getMontoTotal(),0.01);
    }
}