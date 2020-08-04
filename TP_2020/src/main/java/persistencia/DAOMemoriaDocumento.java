package persistencia;

import egreso.documento.Documento;
import egreso.proveedor.Proveedor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOMemoriaDocumento implements DAO<Documento> {
    List<Documento> memoria = new ArrayList<>();

    @Override
    public void agregar(Documento documento) throws IOException {
        memoria.add(documento);
    }

    @Override
    public void eliminar(Documento documento) {
        memoria.remove(documento);
    }

    @Override
    public void modificar(Documento documento) {

    }

    @Override
    public Documento buscar(String codigo) {
        return memoria.stream().filter(p->p.getIdentificador().equals(codigo)).findFirst().get();
    }

    @Override
    public boolean existe(String codigo) {
        return memoria.stream().anyMatch(d ->d.getIdentificador().equals(codigo));
    }

    @Override
    public List<Documento> filtrar(String codigo) {
        return null;
    }
}
