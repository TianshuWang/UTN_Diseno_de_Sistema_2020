package persistencia;

import ingreso.Ingreso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DAOMemoriaIngreso implements DAO<Ingreso>{
    private List<Ingreso> memoria = new ArrayList<>();
    @Override
    public void agregar(Ingreso ingreso) throws IOException {
        memoria.add(ingreso);
    }

    @Override
    public void eliminar(Ingreso ingreso) {
        memoria.remove(ingreso);
    }

    @Override
    public void modificar(Ingreso ingreso) {

    }

    @Override
    public Ingreso buscar(String codigo) {
        return memoria.stream().filter(p->p.getIdentificador().equals(codigo)).findFirst().get();
    }

    @Override
    public boolean existe(String codigo) {
        return memoria.stream().anyMatch(u->u.getIdentificador().equals(codigo));
    }

    @Override
    public List<Ingreso> filtrar(String codigo) {
        return memoria.stream().filter(c->c.getOrganizacion().getNombre().equals(codigo)).collect(Collectors.toList());
    }
}
