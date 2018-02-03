package com.company.store.database;

import com.company.store.model.Cart;
import com.company.store.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final String SQL_SELECT_ALL_PRODUCTS = "SELECT prod_id FROM orders WHERE cart_id = ?";
    private static final String SQL_INSERT_INTO = "INSERT INTO orders(cart_id, prod_id) VALUES (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM orders WHERE cart_id = ? AND prod_id = ?";
    private static final String SQL_DELETE_ALL = "DELETE FROM orders WHERE cart_id = ?";

    public static List<Product> findAllProductsByCartId(Integer cart_id) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_PRODUCTS)) {
            statement.setInt(1, cart_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int prod_id = rs.getInt("prod_id");
                products.add(Product.productDAO.findEntityById(prod_id));
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static boolean create(Cart cart, Integer prod_id) {
        boolean result = false;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO)) {
            statement.setInt(1, cart.getId());
            statement.setInt(2, prod_id);
            result = statement.execute();
            Cart.cartDAO.update(cart);
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean delete(Cart cart, Integer prod_id) {
        boolean result = false;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, cart.getId());
            statement.setInt(2, prod_id);
            result = statement.execute();
            Cart.cartDAO.update(cart);
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean delete(Cart cart) {
        boolean result = false;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ALL)) {
            statement.setInt(1, cart.getId());
            result = statement.execute();
            Cart.cartDAO.update(cart);
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
