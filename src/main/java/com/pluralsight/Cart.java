package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public void addToCart(Product product, int quantity) {
        if (quantity > product.getQuantity()) {
            System.out.println("Not enough stock available!");
            return;
        }
        product.setQuantity(product.getQuantity() - quantity);
        Product cartItem = new Product(product.getId(), product.getName(), product.getPrice(), quantity);
        cartItems.add(cartItem);
        System.out.println(quantity + " " + product.getName() + "(s) added to cart.");
    }

    public void removeFromCart(Product product) {
        cartItems.removeIf(cartItem -> cartItem.getId().equals(product.getId()));
        System.out.println(product.getName() + " removed from cart.");
    }

    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Your cart:");
            for (Product item : cartItems) {
                System.out.println(item);
            }
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (Product item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
