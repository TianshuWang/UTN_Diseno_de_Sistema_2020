package persistencia;

import egreso.egreso.Egreso;

import java.io.IOException;
import java.util.List;

public class Repositorio<T> {
    private DAO<T> dao;
    private Class<T> clase;

    public Repositorio(DAO<T> dao,Class<T> clase){
        this.dao = dao;
        this.clase = clase;
    }

    public void agregar(T object) throws IOException {
        dao.agregar(object);
    }

    public void eliminar(T object) throws IOException {
        dao.eliminar(object);
    }

    public T buscar(String codigo){
        return (T) dao.buscar(codigo);
    }

    public List<T> filtrar(String codigo){
        return dao.filtrar(codigo);
    }

    public Boolean existe(String identificador) {
        return dao.existe(identificador);
    }
}

