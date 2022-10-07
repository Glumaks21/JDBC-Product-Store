package jdbc;

import jdbc.dao.ProductDao;
import jdbc.dao.ProductDaoImpl;
import jdbc.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Client {
    private final List<Product> cart;

    public Client() {
        cart = new ArrayList<>();
    }

    public List<Product> getCartContent() {
        return new ArrayList<>(cart);
    }

    public void addToCart(Product product) {
        Objects.requireNonNull(product);
        cart.add(product);
    }

    public void removeFromCart(Product product) {
        Objects.requireNonNull(product);
        cart.remove(product);
    }

    public void submitPurchase(Product product) {
        if (!cart.contains(product)) {
            throw new IllegalArgumentException();
        }

        DataSourceHolder dataSourceHolder = new FileDataSourceHolder("db.properties");
        ProductDao dao = new ProductDaoImpl(dataSourceHolder.getDataSource());

        if (dao.find(product.getId()).isEmpty()) {
            throw new IllegalStateException("Product is out of stock");
        }

        dao.delete(product.getId());
        cart.remove(product);
    }
}
