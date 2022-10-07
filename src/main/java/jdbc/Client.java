package jdbc;

import jdbc.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final List<Product> cart;

    public Client() {
        cart = new ArrayList<>();
    }

    public List<Product> getCartContent() {
        return new ArrayList<>(cart);
    }

    public void addToCart(Product product) {
        cart.add(product);
    }
}
