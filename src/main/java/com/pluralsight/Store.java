package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        ArrayList<Product> inventory = loadInventory("products.csv");
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);

        int option = -1;
        while (option != 4) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Check Out");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
                continue; // Skip the rest of the loop iteration
            }

            switch (option) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart);
                    break;
                case 3:
                    checkOut(cart, scanner);
                    break;
                case 4:
                    System.out.println("Thank you for visiting!");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
        scanner.close();
    }

    private static ArrayList<Product> loadInventory(String filePath) {
        ArrayList<Product> products = new ArrayList<>();

        // Use getResourceAsStream to load the file as a resource
        try (InputStream inputStream = Store.class.getResourceAsStream("/" + filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                System.out.println("Error: Unable to find file: " + filePath);
                return products; // Return empty list if file not found
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length == 4) {
                    products.add(new Product(data[0], data[1], Double.parseDouble(data[2]), data[3]));
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
        return products;
    }


    private static void displayProducts(ArrayList<Product> inventory, Cart cart, Scanner scanner) {
        System.out.println("\nPRODUCT LIST:");
        inventory.forEach(System.out::println);

        System.out.print("Enter SKU to add to cart or 'back' to return: ");
        String input = scanner.nextLine();
        if (!input.equalsIgnoreCase("back")) {
            inventory.stream()
                    .filter(product -> product.getSku().equalsIgnoreCase(input))
                    .findFirst()
                    .ifPresent(cart::addProduct);
        }
    }

    private static void displayCart(Cart cart) {
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("CART ITEMS:");
        cart.getItems().forEach(System.out::println);
        System.out.printf("Total Amount: $%.2f%n", cart.getTotalAmount());
    }

    private static void checkOut(Cart cart, Scanner scanner) {
        double total = cart.getTotalAmount();
        System.out.printf("Total to pay: $%.2f%n", total);
        System.out.print("Enter payment amount: ");

        double payment = 0;
        if (scanner.hasNextDouble()) {
            payment = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character
        } else {
            System.out.println("Invalid input. Please enter a valid payment amount.");
            scanner.next(); // Clear the invalid input
            return; // Exit the checkout process
        }

        if (payment >= total) {
            System.out.printf("Thank you for your purchase! Change: $%.2f%n", payment - total);
            cart.clear();
            System.out.println("Receipt saved for your purchase.");
        } else {
            System.out.println("Insufficient payment. Please try again.");
        }
    }
}



