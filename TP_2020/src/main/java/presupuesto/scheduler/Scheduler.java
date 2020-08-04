package presupuesto.scheduler;

public class Scheduler {
    private RunValidator runValidator;

    public Scheduler(RunValidator runValidator){
        this.runValidator = runValidator;
    }

    public void run(){
        Thread thread = new Thread(runValidator);
        thread.start();
    }
}
