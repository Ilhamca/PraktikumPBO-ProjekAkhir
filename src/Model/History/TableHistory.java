/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.History;

import Model.History.ModelHistory;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Iam
 */
public class TableHistory extends AbstractTableModel {

    private final List<ModelHistory> historyList;
    private final String[] columnNames = {"ID", "Patient ID", "Queue Number", "Status", "Timestamp"};

    public TableHistory(List<ModelHistory> historyList) {
        this.historyList = historyList;
    }

    @Override
    public int getRowCount() {
        return historyList.size();
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
        ModelHistory history = historyList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> history.getId();
            case 1 -> history.getPatientId();
            case 2 -> history.getQueueNumber();
            case 3 -> history.getStatus().name();
            case 4 -> history.getTimestamp();
            default -> null;
        };
    }
}
