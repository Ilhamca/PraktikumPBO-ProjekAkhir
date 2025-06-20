package Controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import DAO.History.DAOHistory;
import Model.History.ModelHistory;
import Model.History.TableHistory;
import java.util.List;
import javax.swing.table.TableModel;

/**
 *
 * @author Iam
 */
public class ControllerHistory {

    private final DAOHistory daoHistory;

    public ControllerHistory() {
        daoHistory = new DAOHistory();
    }

    public void insert(ModelHistory history) {
        daoHistory.insert(history);
    }

    public void update(ModelHistory history) {
        daoHistory.update(history);
    }

    public void delete(ModelHistory history) {
        daoHistory.delete(history);
    }

    public TableModel searchByPatientId(int id) {
        //ModelHistory resultList =  daoHistory.getById(id);
        List<ModelHistory> resultList = daoHistory.searchByPatientId(id);
        //return new TableHistory(resultList);
        return new TableHistory(resultList);
    }

    public List<ModelHistory> getAll() {
        return daoHistory.getAll();
    }

    public void insertDone(ModelHistory history) {
        daoHistory.insertDone(history);
    }

    public static TableModel getTableModel() {
        DAOHistory dao = new DAOHistory();
        List<ModelHistory> history = dao.getAll();

        System.out.println("DEBUG : Received " + history.size() + " history queue from DAO.");
        return new TableHistory(history);
    }
}
