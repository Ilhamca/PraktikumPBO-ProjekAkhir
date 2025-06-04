/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Patients;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Iam
 */
public class ModelPatients {

    private int id;
    private String name;
    private String phone;
    private LocalDateTime dateOfBirth;

    // Default Constructor - Creates all fields unset (null or 0)
    public ModelPatients() {
    }

    // Parameterized Constructor - Instantly initialize object in one line
    public ModelPatients(int id, String name, String phone, LocalDateTime dateOfBirth) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
