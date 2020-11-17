package organizacion.categoria;
import organizacion.organizacionJuridica.Empresa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

public class Comisionista implements TipoDeActividad {
    @Override
    public void calcularCategoria(Empresa empresa, Sector sector) {

        int cantidadDePersonalEmpresa = empresa.getCantidadDePersona();

        List<Categoria> categoriasSector = sector.getCategorias();
        int sizeCategorias = categoriasSector.size();

        Categoria ultimaCategoria = categoriasSector.get(sizeCategorias - 1);

        if(this.noEstaDentroDelRango(ultimaCategoria, cantidadDePersonalEmpresa)) {
            empresa.setCategoria("Desconocida");
        } else {
            this.buscarLuegoSetearLaCategoria(categoriasSector, cantidadDePersonalEmpresa, empresa);
        }
    }

    private boolean noEstaDentroDelRango(Categoria ultCategoria, int cantidadDePersonal) {
        return ultCategoria.getMaxCantidadPersonal() < cantidadDePersonal;
    }

    private void buscarLuegoSetearLaCategoria(List<Categoria> categoriasSector, int cantidadDePersonasEmpresa, Empresa unaEmpresa) {
        for(int i = 0 ; i < categoriasSector.size() ; i++) {
            Categoria categoria = categoriasSector.get(i);
            if(categoria.getMaxCantidadPersonal() > cantidadDePersonasEmpresa) {
                unaEmpresa.setCategoria(categoria.getNombreCategoria());
                break;
            }
        }
    }
}
