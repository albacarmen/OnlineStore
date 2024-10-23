package com.pluralsight;

import java.io.*;
import java.util.*;

public class Store {
    private static final String FILE_NAME = "products.csv";
    private final List<Product> products = new ArrayList<>();
    private final Cart cart = new Cart();

    public static void main(String[] args) {
        Store store = new Store();
        store.run();
    }


    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to the Online Store! 🛒");
            System.out.println("Choose an option:");
            System.out.println("1) View Products");
            System.out.println("2) Search Product");
            System.out.println("3) Add to Cart");
            System.out.println("4) Remove from Cart");
            System.out.println("5) View Cart");
            System.out.println("6) Checkout");
            System.out.println("7) Exit");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> displayProducts();
                case "2" -> searchProduct(scanner);
                case "3" -> addToCart(scanner);
                case "4" -> removeFromCart(scanner);
                case "5" -> cart.viewCart();
                case "6" -> checkout();
                case "7" -> running = false;
                default -> System.out.println("Invalid option, please try again.");
            }
        }
        scanner.close();
    }

    public void loadProducts(String fileName) {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("No products available.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    String id = data[0];
                    String name = data[1];
                    double price = Double.parseDouble(data[2]);
                    int quantity = Integer.parseInt(data[3]);
                    products.add(new Product(id, name, price, quantity));
                }
            } catch (IOException e) {
                System.err.println("Error loading products: " + e.getMessage());
            }
        }

    private void displayProducts() {
        System.out.println("Available products:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private void searchProduct(Scanner scanner) {
        System.out.print("Enter product ID or name to search: ");
        String input = scanner.nextLine().trim();
        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(input) || product.getName().equalsIgnoreCase(input)) {
                System.out.println(product);
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private void addToCart(Scanner scanner) {
        System.out.print("Enter product ID to add to cart: ");
        String id = scanner.nextLine().trim();
        Product product = findProductById(id);

        if (product != null) {
            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            cart.addToCart(product, quantity);
        } else {
            System.out.println("Product not found.");
        }
    }

    private void removeFromCart(Scanner scanner) {
        System.out.print("Enter product ID to remove from cart: ");
        String id = scanner.nextLine().trim();
        Product product = findProductById(id);

        if (product != null) {
            cart.removeFromCart(product);
        } else {
            System.out.println("Product not found.");
        }
    }

    private Product findProductById(String id) {
        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }

    private void checkout() {
        double total = cart.calculateTotal();
        if (total > 0) {
            System.out.println("Total: $" + String.format("%.2f", total));
            System.out.println("Thank you for your purchase!");
        } else {
            System.out.println("Your cart is empty.");
        }
    }
}

