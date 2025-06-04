/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Users.DAOUsers;
import Model.Users.ModelUsers;
import java.util.List;

/**
 *
 * @author Iam
 */
public class ControllerUsers {

    private DAOUsers dao;

    public ControllerUsers() {
        dao = new DAOUsers();
    }

    public void insert(ModelUsers user) {
        dao.insert(user);
    }

    public void update(ModelUsers user) {
        dao.update(user);
    }

    public void delete(ModelUsers user) {
        dao.delete(user);
    }

    public ModelUsers getById(int id) {
        return dao.getById(id);
    }

    public List<ModelUsers> getAll() {
        return dao.getAll();
    }

    public ModelUsers login(String username, String password) {
        return dao.login(username, password);
    }
}
