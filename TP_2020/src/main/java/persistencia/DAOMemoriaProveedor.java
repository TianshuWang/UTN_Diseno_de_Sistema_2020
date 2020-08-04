package persistencia;

import egreso.proveedor.Proveedor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOMemoriaProveedor implements DAO<Proveedor> {
    private List<Proveedor> memoria = new ArrayList<>();

    @Override
    public void agregar(Proveedor proveedor) {
        memoria.add(proveedor);
    }

    @Override
    public void eliminar(Proveedor proveedor) {
        memoria.remove(proveedor);
    }

    @Override
    public void modificar(Proveedor proveedor) {

    }

    @Override
    public Proveedor buscar(String codigo) {
        return memoria.stream().filter(p->p.getIdentificador().equals(codigo)).findFirst().get();
    }

    @Override
    public boolean existe(String codigo) {
        return memoria.stream().anyMatch(p->p.getIdentificador().equals(codigo));
    }

    @Override
    public List<Proveedor> filtrar(String codigo) {
        return null;
    }
}
