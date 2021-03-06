package com.company.store.database;

import com.company.store.model.Cart;
import com.company.store.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<Integer, User> {
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_SELECT_USER_ON_ID = "SELECT * FROM user WHERE id = ?";
    private static final String SQL_SELECT_ID_ON_USER = "SELECT id FROM user WHERE login = ? OR email = ?";
    private static final String SQL_DELETE_USER_ON_ID = "DELETE FROM user WHERE id = ?";
    private static final String SQL_INSERT_INTO_USER = "INSERT INTO user(email, cart_id, login, password)" +
            " VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE user set password = ? WHERE id = ?";
    private static final String SQL_SELECT_ID = "SELECT id FROM user WHERE login = ? AND password = ?";

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (rs.next()) {
                int id = rs.getInt("id");
                String login = rs.getString("login");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int cart_id = rs.getInt("cart_id");
                users.add(new User(id, login, email, password, Cart.cartDAO.findEntityById(cart_id)));
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findEntityById(Integer id) {
        User user = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_ON_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String login = rs.getString("login");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int cart_id = rs.getInt("cart_id");
                user = new User(id, login, email, password, Cart.cartDAO.findEntityById(cart_id));
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Integer findIdByEntity(User entity) {
        return findIdByEntity(entity.getLogin(), entity.getEmail());
    }

    @Override
    public boolean delete(Integer id) {
        boolean result = false;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_ON_ID)) {
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
    public Integer create(User entity) {
        Integer id = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_USER)) {
            id = findIdByEntity(entity);
            if (id == null) {
                statement.setString(1, entity.getEmail());
                statement.setInt(2, entity.getCart().getId());
                statement.setString(3, entity.getLogin());
                statement.setString(4, entity.getPassword());
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
    public boolean update(User entity) {
        boolean result = false;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getPassword());
            statement.setInt(2, entity.getId());
            result = statement.execute();
        } catch (SQLException e) {
            System.err.println("SQL Exception (request or table failed):" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Integer authentication(String login, String password) {
        Integer id = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ID)) {
            statement.setString(1, login);
            statement.setString(2, password);
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

    public Integer findIdByEntity(String login, String email) {
        Integer id = null;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ID_ON_USER)) {
            statement.setString(1, login);
            statement.setString(2, email);
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
}
