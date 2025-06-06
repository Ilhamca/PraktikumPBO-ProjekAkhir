package Controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import View.StaffDashboard.StaffDashboard;
import View.AdminDashboard.AdminDashboard;
import DAO.Users.DAOUsers;
import Model.Users.ModelUsers;
import Model.Users.TableUsers;

import View.Login.LoginForm;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

/**
 *
 * @author Iam
 */
public class ControllerUsers {

    private final DAOUsers dao;

    public ControllerUsers() {
        dao = new DAOUsers();
    }

    public void insert(ModelUsers user) {
        dao.insert(user);
    }

    public void update(ModelUsers user) {
        dao.update(user);
    }

    public void delete(ModelUsers user) {
        dao.delete(user);
    }

    public ModelUsers getById(int id) {
        return dao.getById(id);
    }

    public List<ModelUsers> getAll() {
        return dao.getAll();
    }

    public ModelUsers login(String username, String password) {
        return dao.login(username, password);
    }

    public void handleLogin(LoginForm form, String username, String password) {
        ModelUsers user = login(username, password);

        if (user != null) {
            // Show message (optional)
            JOptionPane.showMessageDialog(form, "Login successful as " + user.getRole());

            // Open role-based dashboard
            switch (user.getRole()) {
                case ADMIN -> {
                    AdminDashboard adminDash = new AdminDashboard(user);
                    adminDash.setLocationRelativeTo(null);
                    adminDash.setVisible(true);
                }
                

                case STAFF -> {
                    StaffDashboard staffDash = new StaffDashboard(user);
                    staffDash.setLocationRelativeTo(null);
                    staffDash.setVisible(true);
                }
            }

            // Close login form
            form.dispose();

        } else {
            JOptionPane.showMessageDialog(form, "Username or Password incorrect");
        }
    }

    public static TableModel getTableModel() {
        DAOUsers dao = new DAOUsers();
        List<ModelUsers> users = dao.getAll();
        return new TableUsers(users);
    }

    public static void addUser(String username, String password, JPanel parentPanel) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(parentPanel, "Please fill all fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = DAOUsers.insertUser(username, password);

        if (success) {
            JOptionPane.showMessageDialog(parentPanel, "User added successfully!");
        } else {
            JOptionPane.showMessageDialog(parentPanel, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
