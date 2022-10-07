package jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    Optional<T> find(int id);
    void save(T entity);
    void update(T entity);
    void delete(int id);

    Optional<List<T>> findAll();
}
