package com.company.store.model;

import com.company.store.database.ProductDAO;
import javafx.beans.property.*;

public class Product {
    private int id;
    private StringProperty productName;
    private Category category;
    private DoubleProperty price;
    private StringProperty description;
    public static ProductDAO productDAO = new ProductDAO();

    public Product() {
        this("name", 0);
    }

    public Product(String productName, double price) {
        this.productName = new SimpleStringProperty(productName);
        this.category = new Category();
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty("");
        this.id = productDAO.create(this);
    }

    public Product(int id, String productName, Category category, Double price, String description) {
        this.id = id;
        this.productName = new SimpleStringProperty(productName);
        this.category = category;
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
