package com.company.store.database;

import com.company.store.model.Category;
import com.company.store.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends AbstractDAO<Integer, Product> {
    private static final String SQL_SELECT_ALL_PRODUCTS = "SELECT * FROM product";
    private static final String SQL_SELECT_PRODUCT_ON_ID = "SELECT * FROM product WHERE id = ?";
    private static final String SQL_SELECT_ID_ON_PRODUCT = "SELECT id FROM product WHERE name = ? AND price = ? AND " +
            "description = ? AND catalog_id = ?";
    private static final String SQL_DELETE_PRODUCT_ON_ID = "DELETE FROM product WHERE id = ?";
    private static final String SQL_INSERT_INTO_PRODUCT = "INSERT INTO product(name, catalog_id, price, description)" +
            " VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE product set name = ? , price = ? , description = ? , " +
            "catalog_id = ? WHERE id = ?";

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_PRODUCTS);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                String description = rs.getString("description");
                int catalog_id = rs.getInt("catalog_id");
                products.add(new Product(id, name, new Category(catalog_id), price, description));
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findEntityById(Integer id) {
        Product product = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCT_ON_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                String description = rs.getString("description");
                int catalog_id = rs.getInt("catalog_id");
                product = new Product(id, name, new Category(catalog_id), price, description);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Integer findIdByEntity(Product entity) {
        Integer id = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ID_ON_PRODUCT)) {
            statement.setString(1, entity.getProductName());
            statement.setDouble(2, entity.getPrice());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, Category.categoryDAO.findIdByEntity(entity.getCategory()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean delete(Integer id) {
        boolean result = false;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRODUCT_ON_ID)) {
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
    public Integer create(Product entity) {
        Integer id = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_PRODUCT)) {
            id = findIdByEntity(entity);
            if (id == null) {
                statement.setString(1, entity.getProductName());
                statement.setInt(2, Category.categoryDAO.findIdByEntity(entity.getCategory()));
                statement.setDouble(3, entity.getPrice());
                statement.setString(4, entity.getDescription());
                statement.execute();
                id = findIdByEntity(entity);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean update(Product entity) {
        boolean result = false;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getProductName());
            statement.setDouble(2, entity.getPrice());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, Category.categoryDAO.findIdByEntity(entity.getCategory()));
            statement.setInt(5, entity.getId());
            result = statement.execute();
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
