package com.company.store.database;

import com.company.store.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends AbstractDAO<Integer, Category> {
    private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM catalog";
    private static final String SQL_SELECT_CATEGORY_ON_ID = "SELECT * FROM catalog WHERE id = ?";
    private static final String SQL_SELECT_ID_ON_CATEGORY = "SELECT id FROM catalog WHERE name = ?";
    private static final String SQL_DELETE_CATEGORY_ON_ID = "DELETE FROM catalog WHERE id = ?";
    private static final String SQL_INSERT_INTO_CATEGORY = "INSERT INTO catalog VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE catalog set name = ? WHERE id = ?";

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_CATEGORIES);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                categories.add(new Category(id, name));
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category findEntityById(Integer id) {
        Category category = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CATEGORY_ON_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                category = new Category(id, name);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public Integer findIdByEntity(Category entity) {
        Integer id = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ID_ON_CATEGORY)) {
            statement.setString(1, entity.getCategoryName());
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
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CATEGORY_ON_ID)) {
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
    public Integer create(Category entity) {
        Integer id = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_CATEGORY)) {
            id = findIdByEntity(entity);
            if (id == null) {
                statement.setString(1, entity.getCategoryName());
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
    public boolean update(Category entity) {
        boolean result = false;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getCategoryName());
            statement.setInt(2, entity.getId());
            result = statement.execute();
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
