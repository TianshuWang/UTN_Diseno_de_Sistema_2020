package organizacion.categoria;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sector {
    private int id;
    private String nombreSector;
    private List<Categoria> categorias;

    private Sector(){

    }

    public Sector(String nombre) {
        this.nombreSector = nombre;
        this.categorias = new ArrayList<Categoria>();
    }

    public void agregarCategorias(Categoria... categoria) {
        this.categorias.addAll(Arrays.asList(categoria));
    }

    public List<Categoria> getCategorias() {
        return this.categorias;
    }

    public String getNombreSector() {
        return nombreSector;
    }

    private void setNombreSector(String nombreSector) {
        this.nombreSector = nombreSector;
    }
}
