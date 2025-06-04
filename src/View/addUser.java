/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
/**
 *
 * @author Iam
 */
public class addUser extends javax.swing.JPanel {

    private javax.swing.JTextField staffIdField;
    private javax.swing.JTextField usernameField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;

    public addUser() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        javax.swing.JLabel staffIdLabel = new javax.swing.JLabel("Staff ID:");
        javax.swing.JLabel usernameLabel = new javax.swing.JLabel("Username:");
        javax.swing.JLabel passwordLabel = new javax.swing.JLabel("Password:");

        staffIdField = new javax.swing.JTextField(20);
        usernameField = new javax.swing.JTextField(20);
        passwordField = new javax.swing.JPasswordField(20);

        addButton = new javax.swing.JButton("Add User");
        cancelButton = new javax.swing.JButton("Cancel");

        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        cancelButton.addActionListener(e -> {
            staffIdField.setText("");
            usernameField.setText("");
            passwordField.setText("");
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(addButton)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(staffIdLabel)
                                .addComponent(usernameLabel)
                                .addComponent(passwordLabel))
                            .addGap(18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(staffIdField)
                                .addComponent(usernameField)
                                .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                    .addGap(18)
                    .addComponent(cancelButton)
                    .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(40)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(staffIdLabel)
                        .addComponent(staffIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel)
                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addButton)
                        .addComponent(cancelButton))
                    .addContainerGap(40, Short.MAX_VALUE))
        );
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String staffId = staffIdField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (staffId.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = Koneksi.getConnection();
            String sql = "INSERT INTO users (staff_id, username, password) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, staffId);
            pst.setString(2, username);
            pst.setString(3, password);

            int inserted = pst.executeUpdate();
            if (inserted > 0) {
                JOptionPane.showMessageDialog(this, "User added successfully!");
                staffIdField.setText("");
                usernameField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
