/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.History.DAOHistory;
import DAO.Queue.DAOQueue;
import Model.Connector;
import Model.History.ModelHistory;
import Model.Queue.ModelQueue;
import Model.Queue.TableQueue;
import View.StaffDashboard.StaffDashboard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 * @author Iam
 */
public class ControllerQueue {

    private DAOQueue dao;
    private DAOHistory daoHistory;

    public ControllerQueue() {
        dao = new DAOQueue();
        this.daoHistory = new DAOHistory();
    }

    public void insert(ModelQueue queue) {
        dao.insert(queue);
    }

    public void update(ModelQueue queue) {
        dao.update(queue);
    }

    public void delete(ModelQueue queue) {
        dao.delete(queue);
    }

    public ModelQueue getById(int id) {
        return dao.getById(id);
    }

    public List<ModelQueue> getAll() {
        return dao.getAll();
    }

    public static TableModel getTableModel() {
        DAOQueue dao = new DAOQueue();
        List<ModelQueue> queueList = dao.getAll();
        return new TableQueue(queueList);
    }

    public void callQueue(StaffDashboard view, int patientId) {
        if (dao.isPatientWaiting(patientId)) {
            JOptionPane.showMessageDialog(view, "Patient is already in the queue.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
            return; // Stop the process here.
        }

        try {
            ModelQueue queue = new ModelQueue();
            queue.setId(0);
            queue.setPatientId(patientId);
            queue.setStatus(ModelQueue.Status.WAITING);
            queue.setDate(LocalDateTime.now());
            dao.insert(queue);
            JOptionPane.showMessageDialog(view, "Patient with ID " + patientId + " has been added to the queue.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Could not add patient to queue. They may already be in it or a database error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public ModelQueue callNextPatientInQueue(StaffDashboard view) {
        // Call the DAO method to find and update the next patient
        ModelQueue calledPatient = dao.callNextPatient();

        if (calledPatient == null) {
            // If the DAO returns null, it means no one is waiting
            JOptionPane.showMessageDialog(view, "No patients are waiting in the queue.", "Queue Empty", JOptionPane.INFORMATION_MESSAGE);
        }

        // Return the patient object (or null) to the view
        return calledPatient;
    }

    public void skipPatient(StaffDashboard view, int patientId, int status) {
        ModelQueue patientToSkip = dao.findCalledPatientByPatientId(patientId);


        if (patientToSkip == null) {
            JOptionPane.showMessageDialog(view, "Could not find a 'CALLED' patient with ID " + patientId + ". Please 'Call' a patient first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ModelHistory historyRecord = new ModelHistory();
        historyRecord.setPatientId(patientToSkip.getPatientId());
        historyRecord.setQueueNumber(patientToSkip.getId()); // Gets the correct queue ID
        historyRecord.setStatus(ModelHistory.Status.SKIPPED);
        historyRecord.setDate(LocalDateTime.now());


        try {
            String statusText;
            if (status == 0) {
            statusText = "skipped";
            daoHistory.insert(historyRecord);                
            } else {
                statusText = "finished";
            daoHistory.insertDone(historyRecord);
            }

            dao.delete(patientToSkip);
            JOptionPane.showMessageDialog(view, "Patient has been " + statusText, "Patient " + statusText, JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: Could not move patient to history.", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public ModelQueue callNextPatient() {
        Integer queueIdToCall = null;

        // First, find the ID of the next patient who is 'WAITING'.
        String findQuery = "SELECT id FROM queue WHERE status = 'WAITING' ORDER BY id ASC LIMIT 1;";
        try (PreparedStatement findStmt = Connector.Connect().prepareStatement(findQuery)) {
            ResultSet rs = findStmt.executeQuery();
            if (rs.next()) {
                queueIdToCall = rs.getInt("id");
            } else {
                return null; // No one is waiting in the queue.
            }
        } catch (SQLException e) {
            System.out.println("Error finding next patient: " + e.getLocalizedMessage());
            return null;
        }

        // If we found a waiting patient, now we try to update their status.
        if (queueIdToCall != null) {
            String updateQuery = "UPDATE queue SET status = 'CALLED' WHERE id = ?;";
            try (PreparedStatement updateStmt = Connector.Connect().prepareStatement(updateQuery)) {
                updateStmt.setInt(1, queueIdToCall);
                int rowsUpdated = updateStmt.executeUpdate();

                // Only proceed if the update was successful (1 row affected).
                if (rowsUpdated > 0) {
                    // Now that the status is updated, fetch the full patient details to return.
                    return getFullQueueDetailsById(queueIdToCall);
                }
            } catch (SQLException e) {
                System.out.println("Error updating patient status to CALLED: " + e.getLocalizedMessage());
            }
        }

        return null; // Return null if the update failed or for any other reason.
    }

    // ADD THIS NEW HELPER METHOD to get the full details after a successful call.
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
}
