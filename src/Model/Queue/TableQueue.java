/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Queue;

import java.time.LocalDateTime;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * @author Iam
 */
public class TableQueue extends AbstractTableModel {

    private final List<ModelQueue> queueList;
    private final String[] columnNames = {"ID", "Patient ID", "Status", "Time"};

    public TableQueue(List<ModelQueue> queueList) {
        this.queueList = queueList;
    }

    @Override
    public int getRowCount() {
        return queueList.size();
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
        // Get the queue object for the given row
        ModelQueue queueItem = queueList.get(rowIndex);

        // Return the specific piece of data based on the column index
        // This is where you connect your data to the columns.
        switch (columnIndex) {
            case 0:
                return queueItem.getId();
            case 1:
                return queueItem.getPatientId();
            case 2:
                return queueItem.getStatus();
            case 3:
                // THIS IS THE KEY: Return the date object for the third column
                return queueItem.getDate();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // This helps the table use the correct renderer and sorter for each column
        switch (columnIndex) {
            case 0:
            case 1:
                return Integer.class;
            case 2:
                return ModelQueue.Status.class;
            case 3:
                return LocalDateTime.class; // Identify the date column's class
            default:
                return Object.class;
        }
    }
}
