package jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDao {
    public Product getProductById(int id) {
        Product product = null;
        DataSource dataSource = ConnectionHolder.getInstance();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT name, cost, expire_date FROM products p " +
                        "JOIN product_types pt ON(p.product_type_id = pt.id) " +
                        "WHERE p.id = ?;");
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                product = new Product(result.getString("name"),
                                    result.getDouble("cost"),
                                    result.getDate("expire_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    public Product getProductByName(String name) {
        Product product = null;
        DataSource dataSource = ConnectionHolder.getInstance();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT name, cost, expire_date FROM products p " +
                        "JOIN product_types pt ON(p.product_type_id = pt.id) " +
                        "WHERE pt.name = ?;");
            statement.setString(1, name);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                product = new Product(result.getString("name"),
                        result.getDouble("cost"),
                        result.getDate("expire_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }
}

