package APIAsociacion;

public class Operacion {
    public String fechaDeOperacion;
    public int id;
    public double montoTotal;

    public String getFechaDeOperacion() {
        return fechaDeOperacion;
    }

    public int getId() {
        return id;
    }

    public double getMontoTotal() {
        return montoTotal;
    }


    public String getInfoOperacion() {
        return "{ fechaDeOperacion= " + this.fechaDeOperacion + ", id= " + this.id + ", montoTotal: " + this.montoTotal + " }";
    }
}
