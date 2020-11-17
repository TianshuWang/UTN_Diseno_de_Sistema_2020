package organizacion.categoria;

import organizacion.organizacionJuridica.Empresa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

public class NoComisionista implements TipoDeActividad {
    @Override
    public void calcularCategoria(Empresa empresa, Sector sector) {
        float ventaAnualEmpresa = empresa.getVentaAnual();

        List<Categoria> categoriasSector = sector.getCategorias();
        int sizeCategorias = categoriasSector.size();

        Categoria ultimaCategoria = categoriasSector.get(sizeCategorias - 1);

        if(this.noEstaDentroDelRango(ultimaCategoria, ventaAnualEmpresa)) {
            empresa.setCategoria("Desconocida");
        } else {
            this.buscarLuegoSetearLaCategoria(categoriasSector, ventaAnualEmpresa, empresa);
        }
    }

    private boolean noEstaDentroDelRango(Categoria ultCategoria, float ventaAnualEmpresa) {
        return ultCategoria.getMontoMaximo() < ventaAnualEmpresa;
    }

    private void buscarLuegoSetearLaCategoria(List<Categoria> categoriasSector, float ventaAnualEmpresa, Empresa unaEmpresa) {
        for(int i = 0 ; i < categoriasSector.size() ; i++) {
            Categoria categoria = categoriasSector.get(i);
            if(categoria.getMontoMaximo() > ventaAnualEmpresa) {
                unaEmpresa.setCategoria(categoria.getNombreCategoria());
                break;
            }
        }
    }
}
