package jdbc.dao;

import jdbc.entity.Product;

import java.util.Optional;

public interface ProductDao extends CrudDao<Product> {
    Optional<Product> findByName(String name);
}
