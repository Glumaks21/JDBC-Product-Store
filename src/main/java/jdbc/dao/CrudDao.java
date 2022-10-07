package jdbc.dao;

import java.util.List;

public interface CrudDao<T> {
    T find(int id);
    void save(T entity);
    void update(T entity);
    void delete(int id);

    List<T> findAll();
}
