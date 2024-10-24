package com.pluralsight;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product) {
        items.add(product);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public double getTotalAmount() {
        return items.stream().mapToDouble(Product::getPrice).sum();
    }

    public ArrayList<Product> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}

