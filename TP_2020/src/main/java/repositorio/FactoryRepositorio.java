package repositorio;


import java.util.HashMap;

public class FactoryRepositorio {
    private static HashMap<String, Repositorio> repos;

    static {
        repos = new HashMap<>();
    }

    public static <T> Repositorio<T> get(Class<T> type){
        Repositorio<T> repo;
        if(repos.containsKey(type.getName())){
            repo = repos.get(type.getName());
        }
        else{
            repo = new Repositorio<>(new DAOHibernate<>(type));
            repos.put(type.toString(), repo);
        }
        return repo;
    }
}