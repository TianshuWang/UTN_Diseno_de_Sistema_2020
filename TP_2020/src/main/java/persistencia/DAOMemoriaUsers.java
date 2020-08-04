package persistencia;

import usuario.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DAOMemoriaUsers implements DAO<Usuario>{
    private List<Usuario> memoria = new ArrayList<>();

    @Override
    public void agregar(Usuario user) {
        memoria.add(user);
    }

    @Override
    public void eliminar(Usuario user) {
        memoria.remove(user);
    }

    @Override
    public void modificar(Usuario user) {
    }

    @Override
    public Usuario buscar(String codigo) {
        return memoria.stream().filter(u->u.getUsername().equals(codigo)).findFirst().get();
    }

    @Override
    public boolean existe(String codigo) {
        return memoria.stream().anyMatch(u->u.getUsername().equals(codigo));
    }

    @Override
    public List<Usuario> filtrar(String codigo) {
        return memoria.stream().filter(u->u.getOrganizacion().getNombre().equals(codigo)).collect(Collectors.toList());
    }

}
