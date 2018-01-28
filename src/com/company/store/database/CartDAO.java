package com.company.store.database;

import com.company.store.model.Cart;
import com.company.store.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartDAO extends AbstractDAO<Integer, Cart> {
    private static final String SQL_SELECT_ALL_CARTS = "SELECT * FROM cart";
    @Override
    public List<Cart> findAll() {
        List<Cart> carts = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_CARTS);
            while (rs.next()) {
                int id = rs.getInt("id");
                Double price = rs.getDouble("price");
                //todo: add list<Product>
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
        return null;
    }

    @Override
    public Integer findIdByEntity(Cart entity) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Integer create(Cart entity) {
        return null;
    }

    @Override
    public boolean update(Cart entity) {
        return false;
    }
}
