/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Users;

import DAO.InterfaceDAO;
import Model.Connector;
import Model.Users.ModelUsers;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author Iam
 */
public class DAOUsers implements InterfaceDAO<ModelUsers> {

    @Override
    public void insert(ModelUsers obj) {
        try {
            String query = "INSERT INTO users (username, password, role) VALUES (?,?,?)";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, obj.getUsername());
            statement.setString(2, obj.getPassword());
            statement.setString(3, obj.getRole().name());

            statement.executeUpdate();
            System.out.println("Successfully inserted into users");
            statement.close();

        } catch (SQLException e) {
            System.out.println("Input Failed: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(ModelUsers obj) {
        try {
            String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, obj.getUsername());
            statement.setString(2, obj.getPassword());
            statement.setString(3, obj.getRole().name());
            statement.setInt(4, obj.getId());
        } catch (SQLException e) {
            System.out.println("Update Failed: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(ModelUsers obj) {
        try {
            String query = "DELETE FROM users WHERE id=?";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, obj.getId());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Input Failed: " + e.getLocalizedMessage());
        }
    }

    @Override
    public ModelUsers getById(int id) {
        ModelUsers users = null;
        try {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);

            var result = statement.executeQuery();
            if (result.next()) {
                users = new ModelUsers(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("password"),
                        ModelUsers.Role.valueOf(result.getString("Status").toUpperCase())
                );
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed getting id: " + e.getLocalizedMessage());
        }
        return users;
    }

    @Override
    public List<ModelUsers> getAll() {
        List<ModelUsers> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM users ORDER BY role ASC";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            var result = statement.executeQuery();

            while (result.next()) {
                ModelUsers history = new ModelUsers(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("password"),
                        ModelUsers.Role.valueOf(result.getString("role").toUpperCase())
                );
                list.add(history);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("getAll Failed: " + e.getLocalizedMessage());
        }
        return list;
    }

    public ModelUsers login(String username, String password) {
        ModelUsers user = null;
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password); // Hash this in real apps

            var result = statement.executeQuery();

            if (result.next()) {
                user = new ModelUsers(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("password"),
                        ModelUsers.Role.valueOf(result.getString("role").toUpperCase())
                );
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return user;
    }
        

    public static boolean insertUser(String username, String password) {
        try {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pst = Connector.Connect().prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            int inserted = pst.executeUpdate();
            pst.close();

            return inserted > 0;
        } catch (Exception e) {
            System.err.println("DB Error: " + e.getMessage());
            return false;
        }
    }
}
