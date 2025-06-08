/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Patients.DAOPatients;
import Model.Patients.*;
import Model.Patients.ModelPatients;
import View.StaffDashboard.StaffDashboard;
import javax.swing.table.TableModel;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Iam
 */
public class ControllerPatients {

    private DAOPatients dao;

    public ControllerPatients() {
        this.dao = new DAOPatients();
    }

    public void insert(StaffDashboard staffDash, ModelPatients patient) {
        dao.insert(patient);
        JOptionPane.showMessageDialog(staffDash, "Patient added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        staffDash.clearAddFields();
        staffDash.refreshTable();
    }

    public void update(StaffDashboard staffDash, ModelPatients patient) {
        dao.update(patient);
        JOptionPane.showMessageDialog(staffDash, "Patient updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        staffDash.clearAddFields();
        staffDash.refreshTable();
    }

    public void delete(ModelPatients patient) {
        dao.delete(patient);
    }

    public TableModel searchByName(String name){
        List<ModelPatients> searchResult = dao.searchByName(name);
        TableModel resultModel = new TablePatients(searchResult);
        return resultModel;
    }
    
    public ModelPatients getById(int id) {
        return dao.getById(id);
    }

    public List<ModelPatients> getAll() {
        return dao.getAll();
    }

    public static TableModel getTableModel() {
        DAOPatients dao = new DAOPatients();
        List<ModelPatients> patients = dao.getAll();
        
        System.out.println("DEBUG : Received " + patients.size() + " patients from DAO.");
        return new TablePatients(patients);
    }

//    public static void handleAddPatient(JTextField nameField, JTextField phoneField, JDateChooser dateChooser) {
//        String name = nameField.getText();
//        String phone = phoneField.getText();
//        java.util.Date dobUtil = dateChooser.getDate();
//
//        if (name.isEmpty() || phone.isEmpty() || dobUtil == null) {
//            JOptionPane.showMessageDialog(panel, "Please fill all fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Convert java.util.Date to LocalDate
//        LocalDate dob = dobUtil.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
//        ModelPatients patient = new ModelPatients(0, name, phone, dob);
//
//        try {
//            DAOPatients dao = new DAOPatients();
//            dao.insert(patient);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(panel, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
}
