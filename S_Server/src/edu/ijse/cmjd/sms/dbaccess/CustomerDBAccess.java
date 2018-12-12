/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ijse.cmjd.sms.dbaccess;


import edu.ijse.cmjd.sms.db.DBConnection;
import edu.ijse.cmjd.sms.model.Customer;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Dulitha Dissanayake
 */
public class CustomerDBAccess {
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public boolean addCustomer(Customer customer) throws RemoteException, ClassNotFoundException, SQLException {
        try {
            rwLock.writeLock().lock();
            String sql = "insert into Customer values(?,?,?,?)";
            Connection conn = DBConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, customer.getId());
            stm.setObject(2, customer.getName());
            stm.setObject(3, customer.getAddress());
            stm.setObject(4, customer.getContact());
            return stm.executeUpdate() > 0;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public boolean updateCustomer(Customer customer) throws RemoteException, ClassNotFoundException, SQLException {
        try {
            rwLock.writeLock().lock();
            String sql = "update Customer set name=?,address=?,salary=? where id=?";
            Connection conn = DBConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(4, customer.getId());
            stm.setObject(1, customer.getName());
            stm.setObject(2, customer.getAddress());
            stm.setObject(3, customer.getContact());
            return stm.executeUpdate() > 0;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public Customer searchCustomer(String id) throws ClassNotFoundException, SQLException ,RemoteException {
        try {
            rwLock.readLock().lock();
            String sql="select * from customer where id=?";
            Connection conn=DBConnection.getConnection();
            PreparedStatement stm=conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rst=stm.executeQuery();
            if (rst.next()) {
                Customer customer=new Customer(rst.getString("id"),rst.getString("name"),rst.getString("address"),rst.getString("contact"));
                return customer;
            }else{
                return null;
            }
        }finally{
            rwLock.readLock().unlock();
        }
    }
    
    public ArrayList<Customer> viewAllCustomer() throws RemoteException, ClassNotFoundException, SQLException {
        try {
            rwLock.readLock().lock();
            Connection conn = DBConnection.getConnection();

            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery("select * from customer");
            ArrayList<Customer> customerList = new ArrayList<>();
            while (rst.next()) {
                Customer customer = new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact"));
                customerList.add(customer);
            }
           // System.out.println("customerList : "+customerList);
            return customerList;
        } finally {
            rwLock.readLock().unlock();
        }
    }
    
    public boolean deleteCustomer(String id)throws RemoteException, ClassNotFoundException, SQLException {
        try{
            rwLock.writeLock().lock();
            String sql="delete from customer where id=?";
            Connection conn=DBConnection.getConnection();
            PreparedStatement stm=conn.prepareStatement(sql);
            stm.setObject(1, id);
            return stm.executeUpdate()>0;
        }finally{
            rwLock.writeLock().unlock();
        }
    }
}
