/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Queue;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
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
        ModelQueue queue = queueList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> queue.getId();
            case 1 -> queue.getPatientId();
            case 2 -> queue.getStatus().name();
            default -> null;
        };
    }
}
