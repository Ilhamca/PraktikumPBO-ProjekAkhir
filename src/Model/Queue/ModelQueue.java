/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Queue;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Iam
 */
public class ModelQueue {

    private int id, patientId, queueNumber;
    private Status status;
    private LocalDateTime queueDate;

    public enum Status {
        WAITING,
        CALLED,
        SKIPPED,
        DONE
    }

    // Default Constructor - Creates all fields unset (null or 0)
    public ModelQueue() {
    }

    // Parameterized Constructor - Instantly initialize object in one line
    public ModelQueue(int id, int patientId, int queueNumber, Status status, LocalDateTime queueDate) {
        this.id = id;
        this.patientId = patientId;
        this.queueNumber = queueNumber;
        this.status = status;
        this.queueDate = queueDate;
    }

    // 🔽 Getters and Setters
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

    public LocalDateTime getQueueDate() {
        return queueDate;
    }

    public void setQueueDate(LocalDateTime queueDate) {
        this.queueDate = queueDate;
    }
}
