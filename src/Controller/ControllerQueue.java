/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Queue.DAOQueue;
import Model.Queue.ModelQueue;
import java.util.List;

/**
 *
 * @author Iam
 */
public class ControllerQueue {
        private DAOQueue dao;

    public ControllerQueue() {
        dao = new DAOQueue();
    }

    public void insert(ModelQueue queue) {
        dao.insert(queue);
    }

    public void update(ModelQueue queue) {
        dao.update(queue);
    }

    public void delete(ModelQueue queue) {
        dao.delete(queue);
    }

    public ModelQueue getById(int id) {
        return dao.getById(id);
    }

    public List<ModelQueue> getAll() {
        return dao.getAll();
    }
}
