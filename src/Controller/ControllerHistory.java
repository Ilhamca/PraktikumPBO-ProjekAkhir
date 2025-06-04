package Controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import DAO.History.DAOHistory;
import Model.History.ModelHistory;
import java.util.List;

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

    public ModelHistory getById(int id) {
        return daoHistory.getById(id);
    }

    public List<ModelHistory> getAll() {
        return daoHistory.getAll();
    }
}
