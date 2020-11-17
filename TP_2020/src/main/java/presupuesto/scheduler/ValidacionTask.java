package presupuesto.scheduler;
import egreso.Egreso;
import mensaje.mensaje.Mensaje;
import organizacion.Organizacion;
import presupuesto.validator.ValidatorDeTransparencia;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import usuario.usuario.Usuario;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class ValidacionTask extends TimerTask {
    private static Repositorio<Usuario> repoUsuario = FactoryRepositorio.get(Usuario.class);
    private static Repositorio<Egreso> repoEgreso = FactoryRepositorio.get(Egreso.class);
    private static Repositorio<Mensaje> repoMensaje = FactoryRepositorio.get(Mensaje.class);
    private Organizacion organizacion;

    public ValidacionTask(Organizacion organizacion){
        this.organizacion = organizacion;
    }

    @Override
    public void run() {
        try {
            List<Egreso> noValidados = repoEgreso.buscarTodos().stream()
                    .filter(e->e.getUsuario() != null && e.getValidado() == false
                            && e.getUsuario().getOrganizacion().getId() == organizacion.getId() &&
                            e.getPresupuestosRequeridos().isEmpty() == false)
                    .collect(Collectors.toList());
            for(Egreso e:noValidados) {
                Mensaje resultado = new ValidatorDeTransparencia().generarResultadoDeValidacion(e);
                List<Usuario> revisores = repoUsuario.buscarTodos().stream()
                        .filter(u->u.getisRevisor() && u.getOrganizacion().getId() == organizacion.getId())
                        .collect(Collectors.toList());

                Iterator<Usuario> revisoresIt = revisores.iterator();
                while(revisoresIt.hasNext()) {
                    Usuario r = revisoresIt.next();
                    repoMensaje.agregar(resultado);
                    r.getBandejaValidacion().agregarMensaje(resultado);
                    repoUsuario.modificar(r);
                }
                EntityManagerHelper.closeEntityManager();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
