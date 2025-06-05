/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Queue;

import DAO.InterfaceDAO;
import Model.Connector;
import Model.Queue.ModelQueue;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author Iam
 */
public class DAOQueue implements InterfaceDAO<ModelQueue> {

    @Override
    public void insert(ModelQueue obj) {
        try {
            String query = "INSERT INTO queue (patient_id, number, status, date) VALUES (?,?,?,?)";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, obj.getPatientId());
            statement.setInt(2, obj.getQueueNumber());
            statement.setString(3, obj.getStatus().name());
            statement.setTimestamp(4, Timestamp.valueOf(obj.getDate()));

            statement.executeUpdate();
            System.out.println("Successfully inserted into queue");
            statement.close();

        } catch (SQLException e) {
            System.out.println("Input Failed: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(ModelQueue obj) {
        try {
            String query = "UPDATE queue SET patient_id = ?, number = ?, status = ?, date = ? WHERE id = ?";
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
    public void delete(ModelQueue obj) {
        try {
            String query = "DELETE FROM queue WHERE id=?";
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
    public ModelQueue getById(int id) {
        ModelQueue history = null;
        try {
            String query = "SELECT * FROM queue WHERE id = ?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);

            var result = statement.executeQuery();
            if (result.next()) {
                history = new ModelQueue(
                        result.getInt("id"),
                        result.getInt("patient_id"),
                        result.getInt("number"),
                        ModelQueue.Status.valueOf(result.getString("status").toUpperCase()),
                        result.getTimestamp("timestamp").toLocalDateTime()
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
    public List<ModelQueue> getAll() {
        List<ModelQueue> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM queue ORDER BY date DESC";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            var result = statement.executeQuery();

            while (result.next()) {
                ModelQueue queue = new ModelQueue(
                        result.getInt("id"),
                        result.getInt("patient_id"),
                        result.getInt("number"),
                        ModelQueue.Status.valueOf(result.getString("status").toUpperCase()),
                        result.getTimestamp("date").toLocalDateTime()
                );
                list.add(queue);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("getAll Failed: " + e.getLocalizedMessage());
        }
        return list;
    }

    public int getLastQueueNumber() {
        int lastNumber = 0;
        try {
            String sql = "SELECT MAX(queue_number) AS max_number FROM queue";
            PreparedStatement stmt = Connector.Connect().prepareStatement(sql);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                lastNumber = rs.getInt("max_number");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error getting last queue number: " + e.getMessage());
        }
        return lastNumber;
    }
}
