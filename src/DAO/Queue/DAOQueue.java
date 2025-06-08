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
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Iam
 */
public class DAOQueue implements InterfaceDAO<ModelQueue> {

    @Override
    public void insert(ModelQueue obj) {
        try {
            String query = "INSERT INTO queue (patient_id, status, date) VALUES (?,?,?)";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, obj.getPatientId());
            statement.setString(2, obj.getStatus().name());
            statement.setTimestamp(3, Timestamp.valueOf(obj.getDate()));

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
            String query = "UPDATE queue SET patient_id = ?, status = ?, date = ? WHERE id = ?";
            PreparedStatement statement = Connector.Connect().prepareStatement(query);

            statement.setInt(1, obj.getPatientId());
            statement.setString(2, obj.getStatus().name());
            statement.setTimestamp(3, Timestamp.valueOf(obj.getDate()));
            statement.setInt(4, obj.getId());
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

    public boolean isPatientWaiting(int patientId) {
        String query = "SELECT COUNT(*) FROM queue WHERE patient_id = ? AND status = 'WAITING';";

        try (PreparedStatement statement = Connector.Connect().prepareStatement(query)) {
            statement.setInt(1, patientId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // If the count is greater than 0, they are already waiting.
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to check queue status: " + e.getLocalizedMessage());
            // It's safer to assume they might be in the queue if the check fails
            return true;
        }

        return false;
    }

    public ModelQueue callNextPatient() {
        Integer queueIdToCall = null;
        ModelQueue calledPatient = null;

        // First, find the ID of the next patient who is 'WAITING'.
        String findQuery = "SELECT id FROM queue WHERE status = 'WAITING' ORDER BY id ASC LIMIT 1;";
        try (PreparedStatement findStmt = Connector.Connect().prepareStatement(findQuery)) {
            ResultSet rs = findStmt.executeQuery();
            if (rs.next()) {
                queueIdToCall = rs.getInt("id");
            } else {
                System.out.println("DAO: No patients are waiting.");
                return null; // No one is in the queue.
            }
        } catch (SQLException e) {
            System.out.println("DAO Error finding next patient: " + e.getLocalizedMessage());
            return null;
        }

        // If we found a waiting patient, now we update their status to 'CALLED'.
        if (queueIdToCall != null) {
            String updateQuery = "UPDATE queue SET status = 'CALLED' WHERE id = ?;";
            try (PreparedStatement updateStmt = Connector.Connect().prepareStatement(updateQuery)) {
                updateStmt.setInt(1, queueIdToCall);
                int rowsUpdated = updateStmt.executeUpdate();

                // This check is crucial. Only proceed if the update was successful.
                if (rowsUpdated > 0) {
                    System.out.println("DAO DEBUG: Status updated to CALLED for queue ID: " + queueIdToCall);
                    // Now that the status is updated, fetch the full patient details to return.
                    calledPatient = getFullQueueDetailsById(queueIdToCall);
                }
            } catch (SQLException e) {
                System.out.println("DAO Error updating status to CALLED: " + e.getLocalizedMessage());
            }
        }

        return calledPatient;
    }

    // Ensure you also have this helper method from our previous discussion
    private ModelQueue getFullQueueDetailsById(int queueId) {
        String query = "SELECT q.*, p.name FROM queue q " +
                "JOIN patients p ON q.patient_id = p.id " +
                "WHERE q.id = ?;";
        try (PreparedStatement stmt = Connector.Connect().prepareStatement(query)) {
            stmt.setInt(1, queueId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ModelQueue(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        ModelQueue.Status.valueOf(rs.getString("status").toUpperCase()),
                        rs.getTimestamp("date").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching full queue details: " + e.getLocalizedMessage());
        }
        return null;
    }

    public ModelQueue findCalledPatientByPatientId(int patientId) {
        // This query finds a patient using their patient_id and status, which is correct
        String query = "SELECT q.*, p.name FROM queue q " +
                "JOIN patients p ON q.patient_id = p.id " +
                "WHERE q.patient_id = ? AND q.status = 'CALLED' LIMIT 1;";

        try (PreparedStatement stmt = Connector.Connect().prepareStatement(query)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ModelQueue(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        ModelQueue.Status.valueOf(rs.getString("status").toUpperCase()),
                        rs.getTimestamp("date").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching called patient details: " + e.getLocalizedMessage());
        }
        return null; // Return null if not found
    }
}
