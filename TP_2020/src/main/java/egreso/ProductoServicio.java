package egreso;

import direccion_moneda.mercadolibre_api.molde.Moneda;

public class ProductoServicio {
    private String nombre;
    private String descripcion;
    private String tipoProductoServicio; //producto servicio
    private Moneda moneda;

    public ProductoServicio(String nombre, String descripcion,String tipoProductoServicio){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoProductoServicio = tipoProductoServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipoProductoServicio() {
        return tipoProductoServicio;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }
}
