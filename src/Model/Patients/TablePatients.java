/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Patients;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Iam
 */
public class TablePatients extends AbstractTableModel{
    private final List<ModelPatients> patientList;
    private final String[] columnNames = {"ID", "Name", "Date of Birth", "Gender"};

    public TablePatients(List<ModelPatients> patientList) {
        this.patientList = patientList;
    }

    @Override
    public int getRowCount() {
        return patientList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelPatients patient = patientList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return patient.getId();
            case 1:
                return patient.getName();
            case 2:
                return patient.getPhone();
            case 3:
                return patient.getDateOfBirth();
            default:
                return null;
        }
    }
}
