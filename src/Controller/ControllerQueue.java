/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Queue.DAOQueue;
import Model.Queue.ModelQueue;
import Model.Queue.TableQueue;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.table.TableModel;

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

    public static TableModel getTableModel() {
        DAOQueue dao = new DAOQueue();
        List<ModelQueue> queueList = dao.getAll();
        return new TableQueue(queueList);
    }

    public void callQueue(int patientId) {
        int lastQueueNumber = dao.getLastQueueNumber();
        int newQueueNumber = lastQueueNumber + 1;

        ModelQueue queue = new ModelQueue();
        queue.setId(0);
        queue.setPatientId(patientId);
        queue.setStatus(ModelQueue.Status.WAITING);
        queue.setDate(LocalDateTime.now());

        dao.insert(queue);
    }
}
