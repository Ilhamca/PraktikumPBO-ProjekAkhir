/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.History;

import DAO.InterfaceDAO;
import Model.Connector;
import Model.History.ModelHistory;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Iam
 */
public class DAOHistory implements InterfaceDAO<ModelHistory> {

    @Override
    public void insert(ModelHistory obj) {
        try {
            String query = "INSERT INTO history (id, patient_id, queue_number, status, date) VALUES (?,?,?,?,?)";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, 0);
            statement.setInt(2, obj.getPatientId());
            statement.setInt(3, obj.getQueueNumber());
            statement.setString(4, "SKIPPED");
            statement.setTimestamp(5, Timestamp.valueOf(obj.getDate()));

            statement.executeUpdate();
            System.out.println("Successfully inserted into history");
            statement.close();

        } catch (SQLException e) {
            System.out.println("Input Failed: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(ModelHistory obj) {
        try {
            String query = "UPDATE history SET patient_id = ?, queue_number = ?, status = ?, timestamp = ? WHERE id = ?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);

            statement.setInt(1, obj.getPatientId());
            statement.setInt(2, obj.getQueueNumber());
            statement.setString(3, obj.getStatus().name());
            statement.setTimestamp(4, Timestamp.valueOf(obj.getDate()));
            statement.setInt(5, obj.getId());
        } catch (SQLException e) {
            System.out.println("Update Failed: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(ModelHistory obj) {
        try {
            String query = "DELETE FROM history WHERE id=?";
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
    public ModelHistory getById(int id) {
        ModelHistory history = null;
        try {
            String query = "SELECT * FROM history WHERE id = ?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);

            var result = statement.executeQuery();
            if (result.next()) {
                history = new ModelHistory(
                        result.getInt("id"),
                        result.getInt("patient_id"),
                        result.getInt("queue_number"),
                        ModelHistory.Status.valueOf(result.getString("Status").toUpperCase()),
                        result.getTimestamp("Date").toLocalDateTime()
                );
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed getting id: " + e.getLocalizedMessage());
        }
        return history;
    }

    @Override
    public List<ModelHistory> getAll() {
        List<ModelHistory> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM history ORDER BY date DESC";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            var result = statement.executeQuery();

            while (result.next()) {
                ModelHistory history = new ModelHistory(
                        result.getInt("id"),
                        result.getInt("patient_id"),
                        result.getInt("queue_number"),
                        ModelHistory.Status.valueOf(result.getString("status").toUpperCase()),
                        result.getTimestamp("date").toLocalDateTime()
                );
                list.add(history);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("getAll Failed: " + e.getLocalizedMessage());
        }
        return list;
    }

    public void insertDone(ModelHistory obj) {
        try {
            String query = "INSERT INTO history (id, patient_id, queue_number, status, date) VALUES (?,?,?,?,?)";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, 0);
            statement.setInt(2, obj.getPatientId());
            statement.setInt(3, obj.getQueueNumber());
            statement.setString(4, "DONE");
            statement.setTimestamp(5, Timestamp.valueOf(obj.getDate()));

            statement.executeUpdate();
            System.out.println("Successfully inserted into history");
            statement.close();

        } catch (SQLException e) {
            System.out.println("Input Failed: " + e.getLocalizedMessage());
        }
    }
}
