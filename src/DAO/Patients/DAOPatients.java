/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Patients;

import DAO.InterfaceDAO;
import Model.Connector;
import Model.Patients.ModelPatients;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;
/**
 *
 * @author Iam
 */
public class DAOPatients implements InterfaceDAO<ModelPatients> {

    @Override
    public void insert(ModelPatients obj) {
        try {
            String query = "INSERT INTO patients (id, name, phone, dob) VALUES (?,?,?,?)";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getPhone());
            statement.setTimestamp(4, Timestamp.valueOf(obj.getDateOfBirth()));

            statement.executeUpdate();
            System.out.println("Successfully inserted into patients");
            statement.close();

        } catch (SQLException e) {
            System.out.println("Input Failed: " + e.getLocalizedMessage());
        }    
    }

    @Override
    public void update(ModelPatients obj) {
        try {
            String query = "UPDATE patients SET id = ?, name = ?, phone = ?, dob = ? WHERE id = ?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);

            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getPhone());
            statement.setTimestamp(4, Timestamp.valueOf(obj.getDateOfBirth()));
        } catch (SQLException e) {
            System.out.println("Update Failed: " + e.getLocalizedMessage());
        }        
    }

    @Override
    public void delete(ModelPatients obj) {
        try {
            String query = "DELETE FROM patients WHERE id=?";
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
    public ModelPatients getById(int id) {
                ModelPatients patients = null;
                try {
            String query = "SELECT * FROM patients WHERE id = ?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);

            var result = statement.executeQuery();
            if (result.next()) {
                patients = new ModelPatients(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("phone"),
                        result.getTimestamp("dob").toLocalDateTime()
                );
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed getting id: " + e.getLocalizedMessage());
        }
        return patients;
    }

    @Override
    public List<ModelPatients> getAll() {
        List<ModelPatients> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM patients ORDER BY timestamp DESC";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            var result = statement.executeQuery();

            while (result.next()) {
                ModelPatients history = new ModelPatients(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("phone"),
                        result.getTimestamp("dob").toLocalDateTime()
                );
                list.add(history);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("getAll Failed: " + e.getLocalizedMessage());
        }
        return list;
    }
    
}
