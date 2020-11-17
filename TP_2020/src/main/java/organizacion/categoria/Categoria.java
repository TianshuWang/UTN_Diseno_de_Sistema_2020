package organizacion.categoria;

import javax.persistence.*;

public class Categoria {
    private int id;
    private String nombreCategoria;
    private Long montoMaximo;
    private Integer maxCantidadPersonal;
    private int peso;

    private Categoria(){

    }

    public Categoria(String nombre, Long montoMax, Integer maxCantPersonal, int peso) {
        this.nombreCategoria = nombre;
        this.montoMaximo = montoMax;
        this.maxCantidadPersonal = maxCantPersonal;
        this.peso = peso;
    }

    //getters setters
    public long getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(long montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public int getMaxCantidadPersonal() {
        return maxCantidadPersonal;
    }

    public void setMaxCantidadPersonal(int maxCantidadPersonal) {
        this.maxCantidadPersonal = maxCantidadPersonal;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
