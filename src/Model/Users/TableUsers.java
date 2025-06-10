/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Users;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Iam
 */
public class TableUsers extends AbstractTableModel {

    private final List<ModelUsers> userList;

    private final String[] columnNames = {"ID", "Username", "Role"};

    public TableUsers(List<ModelUsers> userList) {
        this.userList = userList;
    }

    @Override
    public int getRowCount() {
        return userList.size();
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
        ModelUsers user = userList.get(rowIndex);
        return switch (columnIndex) {
            case 0 ->
                user.getId();
            case 1 ->
                user.getUsername();
            case 2 ->
                user.getRole();
            default ->
                null;
        };
    }
}
