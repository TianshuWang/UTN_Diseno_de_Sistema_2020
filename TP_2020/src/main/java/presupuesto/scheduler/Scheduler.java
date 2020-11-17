package presupuesto.scheduler;
import getDataConfig.GetDataConfig;
import organizacion.Organizacion;
import java.io.FileNotFoundException;
import java.util.Timer;

public class Scheduler {
    //con timer da posibilidad de correrlo con periodo de dias o meses etc.
    private Timer timer;
    private Integer periodoValidacion;

    public Scheduler() throws FileNotFoundException {
        timer = new Timer();
        periodoValidacion = Integer.parseInt(GetDataConfig.getValue("periodo_validacion"));
    }

    public void run(Organizacion organizacion) throws FileNotFoundException {
        timer.schedule(new ValidacionTask(organizacion),1000,periodoValidacion*1000);
    }
}
