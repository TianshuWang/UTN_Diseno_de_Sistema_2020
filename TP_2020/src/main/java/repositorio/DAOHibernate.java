package repositorio;

import repositorio.entitymanager.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DAOHibernate<T> implements DAO<T> {
    private Class<T> type;

    public DAOHibernate(Class<T> type){
        this.type = type;
    }

    @Override
    public List<T> buscarTodos() {
        EntityManager em = EntityManagerHelper.getEntityManager();
        List<T> list = em.createQuery("from "+this.type.getSimpleName())
                .getResultList();

        return list;
    }

    @Override
    public T buscar(int id) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        T t = em.find(type, id);
        return t;
    }

    @Override
    public T buscar(String id) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        T t = em.find(type, id);

        return t;
    }

    @Override
    public void agregar(Object unObjeto) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.persist(unObjeto);
        EntityManagerHelper.commit();
    }

    @Override
    public void modificar(Object unObjeto) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.merge(unObjeto);
        EntityManagerHelper.commit();
    }

    @Override
    public void eliminar(Object unObjeto) {

    }
}
