package com.company.store.model;

import com.company.store.database.CartDAO;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.List;

public class Cart {
    private int id;
    private List<Product> products;
    private DoubleProperty price;
    public static CartDAO cartDAO = new CartDAO();

    public Cart() {
        this.id = cartDAO.create(this);
        this.price = new SimpleDoubleProperty(0);
    }

    public Cart(int id, List<Product> products, Double price) {
        this.id = id;
        this.products = products;
        this.price = new SimpleDoubleProperty(price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void changePrice(double price) {
        this.price = new SimpleDoubleProperty(getPrice() + price);
    }
}
