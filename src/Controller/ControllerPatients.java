/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Patients.DAOPatients;
import Model.Patients.*;
import Model.Patients.ModelPatients;
import javax.swing.table.TableModel;
import java.util.List;

/**
 *
 * @author Iam
 */
public class ControllerPatients {

    private DAOPatients dao;

    public ControllerPatients() {
        dao = new DAOPatients();
    }

    public void insert(ModelPatients patient) {
        dao.insert(patient);
    }

    public void update(ModelPatients patient) {
        dao.update(patient);
    }

    public void delete(ModelPatients patient) {
        dao.delete(patient);
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
        return new TablePatients(patients);
    }

}
