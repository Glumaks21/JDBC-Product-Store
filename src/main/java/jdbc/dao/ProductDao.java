package jdbc.dao;

import jdbc.entity.Product;

public interface ProductDao extends CrudDao<Product> {
    Product findByName(String name);
}
