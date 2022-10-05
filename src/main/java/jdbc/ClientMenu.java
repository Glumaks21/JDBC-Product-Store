package jdbc;

import java.io.InputStream;
import java.util.Scanner;

public class ClientMenu {
    private final Scanner scanner;
    private boolean isRunning;

    public ClientMenu(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException();
        }

        scanner = new Scanner(inputStream);
    }

    public void start() {
        isRunning = true;

        while (isRunning) {
            System.out.println("Main menu");
            System.out.println("1. See products");
            System.out.println("2. Check cart");
            System.out.println("0. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    seeProducts();
                    break;
                case "2":
                    checkCart();
                    break;
                case "0":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }

    private void seeProducts() {

    }

    private void checkCart() {

    }
}
