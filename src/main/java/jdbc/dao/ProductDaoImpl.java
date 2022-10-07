package jdbc.dao;

import jdbc.DataSourceHolder;
import jdbc.FileDataSourceHolder;
import jdbc.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {
    public DataSource dataSource;

    public ProductDaoImpl(DataSource dataSource) {
        Objects.requireNonNull(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Product> find(int id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM products WHERE id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            return Optional.ofNullable(mapToProduct(resultSet));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Product product) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO products(name, cost, producer_id, description)" +
                        "VALUES (?, ?, ?, ?);"
            );
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getCost());
            statement.setInt(3, product.getProducerId());
            statement.setString(4, product.getDescription());
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE products SET name = ?, " +
                            "cost = ?, producer_id = ?, description = ?" +
                            "WHERE id = ?;"
            );
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getCost());
            statement.setInt(3, product.getProducerId());
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM products WHERE id = ?;"
            );
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<List<Product>> findAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");

            List<Product> list = new ArrayList<>();
            Product product;
            while ((product = mapToProduct(resultSet)) != null) {
                list.add(product);
            }

            if (list.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(list);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Product> findByName(String name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM products WHERE name = ?"
            );
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            return Optional.ofNullable(mapToProduct(resultSet));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private Product mapToProduct(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setCost(resultSet.getDouble("cost"));
            product.setProducerId(resultSet.getInt("producer_id"));
            product.setDescription(resultSet.getString("description"));
            return product;
        }
        return null;
    }
}

class Test {
    public static void main(String[] args) {
        DataSourceHolder dataSourceHolder = new FileDataSourceHolder("db.properties");
        ProductDao dao = new ProductDaoImpl(dataSourceHolder.getDataSource());


    }
}
