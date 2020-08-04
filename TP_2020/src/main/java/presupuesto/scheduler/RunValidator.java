package presupuesto.scheduler;

import exceptions.UserException;
import getDataConfig.GetDataConfig;
import presupuesto.validator.ValidatorDeTransparencia;
import usuario.usuario.Usuario;

import java.io.FileNotFoundException;
import java.util.*;

public class RunValidator implements Runnable {
    //si se requiere validar para todos los usuarios, pasamos users como todos los usuarios de una organizacion
    private List<Usuario> users;
    private Integer periodoValidacion;

    public RunValidator(Usuario...user) throws FileNotFoundException {
        users = new ArrayList<>();
        Collections.addAll(users,user);
        periodoValidacion = Integer.parseInt(GetDataConfig.getValue("periodo_validacion"));
        //el period de validacion puede ser configurable por los usuarios en el futuro
        //this.periodoValidacion
    }

    public void run() {
        try {
            while (true) {
                System.out.println("\033[32;4m"+"Scheduler De Validator De Transparencia"+"\033[0m");
                for(Usuario u:users){
                    new ValidatorDeTransparencia().generarResultadoDeValidacion(u);
                }
                Thread.sleep(periodoValidacion * 1000);
            }
        } catch (InterruptedException | UserException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
