/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.History;

import java.time.LocalDateTime;

/**
 *
 * @author Iam
 */
public class ModelHistory {

    private int id, patientId, queueNumber;
    private Status status;
    private LocalDateTime date;

    public enum Status {
        SKIPPED,
        DONE
    }

    // Default Constructor - Creates all fields unset (null or 0)
    public ModelHistory() {
    }

    // Parameterized Constructor - Instantly initialize object in one line
    public ModelHistory(int id, int patientId, int queueNumber, Status status, LocalDateTime date) {
        this.id = id;
        this.patientId = patientId;
        this.queueNumber = queueNumber;
        this.status = status;
        this.date = date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}