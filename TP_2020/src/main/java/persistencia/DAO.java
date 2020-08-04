package persistencia;

import java.io.IOException;
import java.util.List;

public interface DAO<T> {
    public void agregar(T t) throws IOException;
    public void eliminar(T t);
    public void modificar(T t);
    public T buscar(String codigo);
    public boolean existe(String codigo);
    public List<T> filtrar(String codigo);
}
