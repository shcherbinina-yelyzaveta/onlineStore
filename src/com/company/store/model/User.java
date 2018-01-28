package com.company.store.model;

import com.company.store.database.UserDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private int id;
    private StringProperty login;
    private StringProperty email;
    private StringProperty password;
    private Cart cart;
    public static UserDAO userDAO = new UserDAO();

    public User(String login, String email, String password) {
        this.login = new SimpleStringProperty(login);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.cart = new Cart();
//        this.id = userDAO.create(this);
    }

    public User(int id, String login, String email, String password, Cart cart) {
        this.id = id;
        this.login = new SimpleStringProperty(login);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
