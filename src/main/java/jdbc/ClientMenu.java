package jdbc;

import jdbc.dao.DataSourceHolder;
import jdbc.dao.FileDataSourceHolder;
import jdbc.dao.ProductDao;
import jdbc.dao.ProductDaoImpl;
import jdbc.entity.Product;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class ClientMenu {
    private final Client client;
    private boolean isRunning;
    private Scanner scanner;

    public ClientMenu(Client client) {
        Objects.requireNonNull(client);
        this.client = client;
    }

    public void start() {
        scanner = new Scanner(System.in);

        isRunning = true;
        while (isRunning) {
            System.out.println("Main menu");
            System.out.println("1. See products");
            System.out.println("2. Check cart");
            System.out.println("3. Exit");

            switch (scanner.nextLine()) {
                case "1":
                    seeProducts();
                    break;
                case "2":
                    checkCart();
                    break;
                case "3":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Incorrect option");
                    break;
            }
        }
    }

    private void seeProducts() {
        while (isRunning) {
            DataSourceHolder dataSourceHolder = new FileDataSourceHolder("db.properties");
            ProductDao dao = new ProductDaoImpl(dataSourceHolder.getDataSource());

            Optional<List<Product>> response = dao.findAll();
            if (response.isPresent()) {
                List<Product> products = response.get();
                printProductTable(products);
                System.out.println("1. Add to cart <name>");
                System.out.println("2. Back");

                String choice = scanner.nextLine();
                if (choice.startsWith("1")) {
                    String productName = choice.substring(choice.indexOf(" ") + 1);

                    dao.findByName(productName).
                            ifPresentOrElse(client::addToCart,
                            () -> System.out.println("Incorrect product name"));
                } else if (choice.equals("2")) {
                    return;
                } else {
                    System.out.println("Incorrect option");
                }
            } else {
                System.out.println("Products is not found");
                System.out.println("Press any button to come back");

                scanner.nextLine();
                return;
            }
        }
    }

    private void printProductTable(List<Product> products) {
        System.out.printf("%-20s|%-30s|%-10s\n", "Name", "Description", "Cost");
        products.forEach(product -> System.out.printf("%-20s|%-30s|%-10.2f\n",
                product.getName(), product.getDescription(), product.getCost()));
    }

    private void checkCart() {
        while (isRunning) {
            List<Product> selected = client.getCartContent();

            if (selected.isEmpty()) {
                System.out.println("Cart is empty");
                System.out.println("Press any button to come back");
                scanner.nextLine();
                return;
            }

            printProductTable(selected);
            System.out.println("1. Submit all");
            System.out.println("2. Submit <name>");
            System.out.println("3. Cancel all");
            System.out.println("4. Cancel <name>");
            System.out.println("5. Back");

            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                submitAll();
            } else if (choice.startsWith("2")) {
                String name = choice.substring(choice.indexOf(" ") + 1);
                submitSelectedPurchase(name);
            } else if (choice.equals("3")) {
                selected.forEach(client::removeFromCart);
            } else if (choice.startsWith("4")) {
                String name = choice.substring(choice.indexOf(" ") + 1);

                for (Product inCart : selected) {
                    if (inCart.getName().equals(name)) {
                        client.removeFromCart(inCart);
                        break;
                    }
                }
            } else if (choice.equals("5")) {
                return;
            } else {
                System.out.println("Incorrect option");
            }
        }
    }

    private void submitAll() {
        try {
            client.getCartContent().forEach(client::submitPurchase);
        } catch (IllegalStateException e) {
            System.out.println("Product is out of stock");
        }
    }

    private void submitSelectedPurchase(String name) {
        try {
            for (Product inCart : client.getCartContent()) {
                if (inCart.getName().equals(name)) {
                    client.submitPurchase(inCart);
                    break;
                }
            }
        } catch (IllegalStateException e) {
            System.out.println("Product is out of stock");
        }
    }
}
