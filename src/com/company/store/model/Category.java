package com.company.store.model;

import com.company.store.database.CategoryDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category {
    private int id;
    private StringProperty categoryName;
    public static CategoryDAO categoryDAO = new CategoryDAO();

    public Category() {
        this("Other");
    }

    public Category(String categoryName) {
        this.categoryName = new SimpleStringProperty(categoryName);
//        this.id = categoryDAO.create(this);
    }

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName.set(categoryName);
    }

    public Category(int id) {
        this(categoryDAO.findEntityById(id));
    }

    private Category(Category category){
        this.id = category.getId();
        this.categoryName = category.categoryNameProperty();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName.get();
    }

    public StringProperty categoryNameProperty() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
    }
}
