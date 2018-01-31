package com.company.store.database;

import com.company.store.model.Cart;
import com.company.store.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO extends AbstractDAO<Integer, Cart> {
    private static final String SQL_SELECT_ALL_CARTS = "SELECT * FROM cart";
    private static final String SQL_SELECT_CART_ON_ID = "SELECT * FROM cart WHERE id = ?";
    private static final String SQL_DELETE_CART_ON_ID = "DELETE FROM cart WHERE id = ?";
    private static final String SQL_INSERT_INTO_CART = "INSERT INTO cart(price) VALUES (0)";
    private static final String SQL_UPDATE = "UPDATE cart set price = (" +
            "SELECT sum(price) FROM product, orders" +
            "WHERE product.id = prod_id AND cart_id = cart.id)";

    @Override
    public List<Cart> findAll() {
        List<Cart> carts = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_CARTS);
            while (rs.next()) {
                int id = rs.getInt("id");
                Double price = rs.getDouble("price");
                List<Product> products = OrderDAO.findAllProductsByCartId(id);
                carts.add(new Cart(id, products, price));
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return carts;
    }

    @Override
    public Cart findEntityById(Integer id) {
        Cart cart = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CART_ON_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Double price = rs.getDouble("price");
                List<Product> products = OrderDAO.findAllProductsByCartId(id);
                cart = new Cart(id, products, price);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public Integer findIdByEntity(Cart entity) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        boolean result = false;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CART_ON_ID)) {
            statement.setInt(1, id);
            result = statement.execute();
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer create(Cart entity) {
        Integer id = null;
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            statement.addBatch(SQL_INSERT_INTO_CART);
            statement.addBatch("SELECT @@IDENTITY");
            statement.executeBatch();
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean update(Cart entity) {
        boolean result = false;
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            result = statement.execute(SQL_UPDATE);
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
