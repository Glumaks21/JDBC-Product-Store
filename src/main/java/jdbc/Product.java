package jdbc;

import java.util.Date;

public class Product {
    private final String name;
    private final double cost;
    private final Date expireDate;

    public Product(String name, double cost, Date expireDate) {
        if (name == null || cost < 0 || expireDate == null) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.cost = cost;
        this.expireDate = expireDate;
   }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public Date getExpireDate() {
        return expireDate;
    }
}
