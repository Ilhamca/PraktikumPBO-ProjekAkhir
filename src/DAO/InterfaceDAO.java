/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;
/**
 *
 * @author Iam
 */
public interface InterfaceDAO<T> {
    public void insert(T obj);
    public void update(T obj);
    public void delete(T obj);
    T getById(int id);
    public List<T> getAll();
}
