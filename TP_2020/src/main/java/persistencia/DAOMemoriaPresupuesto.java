package persistencia;

import presupuesto.Presupuesto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DAOMemoriaPresupuesto implements DAO<Presupuesto> {
    List<Presupuesto> memoria = new ArrayList<>();

    @Override
    public void agregar(Presupuesto presupuesto) throws IOException {
        memoria.add(presupuesto);
    }

    @Override
    public void eliminar(Presupuesto presupuesto) {
        memoria.remove(presupuesto);
    }

    @Override
    public void modificar(Presupuesto presupuesto) {

    }

    @Override
    public Presupuesto buscar(String codigo) {
        return memoria.stream().filter(p->p.getIdentificador().equals(codigo)).findFirst().get();
    }

    @Override
    public boolean existe(String codigo) {
        return memoria.stream().anyMatch(p->p.getIdentificador().equals(codigo));
    }

    @Override
    public List<Presupuesto> filtrar(String codigo) {
        return null;
    }
}
