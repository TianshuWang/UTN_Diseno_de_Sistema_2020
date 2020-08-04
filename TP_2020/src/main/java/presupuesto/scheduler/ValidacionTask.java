package presupuesto.scheduler;
import exceptions.UserException;
import organizacion.Organizacion;
import persistencia.Repositorio;
import presupuesto.validator.ValidatorDeTransparencia;
import usuario.usuario.Usuario;
import usuario.usuario.UsuarioFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ValidacionTask extends TimerTask {
    private List<Usuario> users;
    private Organizacion organizacion;

    public ValidacionTask(Organizacion organizacion) throws FileNotFoundException {
        users = new ArrayList<>();
        users = UsuarioFactory.getRepositorio().filtrar(organizacion.getNombre());
        this.organizacion = organizacion;
    }

    @Override
    public void run() {
        try {
            System.out.println("\033[32;4m"+"Scheduler De Validator De Transparencia Para Los Usuarios De La Organizacion:"+organizacion.getNombre()+"\033[0m");
            for(Usuario u:users){
                new ValidatorDeTransparencia().generarResultadoDeValidacion(u);
            }
        } catch (UserException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
