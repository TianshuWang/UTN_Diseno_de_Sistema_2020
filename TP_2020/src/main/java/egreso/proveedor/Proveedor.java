package egreso.proveedor;

import direccion_moneda.DireccionPostal;
import egreso.ProductoServicio;

import java.util.*;

public class Proveedor {
    private String nombre;
    private String cuit;
    private DireccionPostal direccionPostal;
    private List<ProductoServicio> productoServicioList;

    public Proveedor(String nombre,String cuit,DireccionPostal direccionPostal,ProductoServicio ...productoServicios){
        this.nombre = nombre;
        this.cuit = cuit;
        this.direccionPostal = direccionPostal;
        this.productoServicioList =new ArrayList<>();
        Arrays.asList(productoServicios).forEach(p->agregarProductoServicio(p));
    }

    private void agregarProductoServicio(ProductoServicio productoServicio){
        this.productoServicioList.add(productoServicio);
        productoServicio.setMoneda(this.direccionPostal.getUbicacion().getMoneda());
    }

    public String getIdentificador() {
        return cuit;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCuit() {
        return cuit;
    }

    public DireccionPostal getDireccionPostal() {
        return direccionPostal;
    }

    public List<ProductoServicio> getProductoServicioList() {
        return productoServicioList;
    }
}
