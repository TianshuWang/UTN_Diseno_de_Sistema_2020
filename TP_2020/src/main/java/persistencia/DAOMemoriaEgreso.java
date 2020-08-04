package persistencia;

import egreso.egreso.Egreso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DAOMemoriaEgreso implements DAO<Egreso>{
    private List<Egreso> memoria = new ArrayList<>();

    @Override
    public void agregar(Egreso egreso) {
        memoria.add(egreso);
    }

    @Override
    public void eliminar(Egreso egreso) {
        memoria.remove(egreso);
    }

    @Override
    public void modificar(Egreso egreso) {

    }

    @Override
    public Egreso buscar(String codigo) {
        return memoria.stream().filter(p->p.getIdentificador().equals(codigo)).findFirst().get();
    }

    @Override
    public boolean existe(String codigo) {
        return memoria.stream().anyMatch(u->u.getIdentificador().equals(codigo));
    }

    @Override
    public List<Egreso> filtrar(String codigo) {
        return memoria.stream().filter(c->c.getOrganizacion().getNombre().equals(codigo)).collect(Collectors.toList());
    }
}
