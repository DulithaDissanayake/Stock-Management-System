/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ijse.cmjd.sms.dbaccess;

import edu.ijse.cmjd.sms.db.DBConnection;
import edu.ijse.cmjd.sms.model.Item;
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
public class ItemDBAccess {
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public boolean addItem(Item item) throws RemoteException, ClassNotFoundException, SQLException {
        try {
            rwLock.writeLock().lock();
            String sql = "insert into Item values(?,?,?,?)";
            Connection conn = DBConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1,item.getCode());
            stm.setObject(2, item.getDescription());
            stm.setObject(3, item.getUnitPrice());
            stm.setObject(4, item.getQtyOnHand());
            return stm.executeUpdate() > 0;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public boolean updateItem(Item item) throws RemoteException, ClassNotFoundException, SQLException {
        try {
            rwLock.writeLock().lock();
            String sql = "update item set description=?,unitPrice=?,qtyOnHand=? where code=?";
            Connection conn = DBConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, item.getDescription());
            stm.setObject(2, item.getUnitPrice());
            stm.setObject(3,item.getQtyOnHand());
            stm.setObject(4, item.getCode());
            return stm.executeUpdate() > 0;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public Item searchItem(String id) throws ClassNotFoundException, SQLException ,RemoteException {
        try {
            rwLock.readLock().lock();
            String sql="select * from item where code=?";
            Connection conn=DBConnection.getConnection();
            PreparedStatement stm=conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rst=stm.executeQuery();
            if (rst.next()) {
                Item item=new Item(rst.getString("code"),rst.getString("description"),rst.getString("unitPrice"),rst.getString("qtyOnHand"));
                return item;
            }else{
                return null;
            }
        }finally{
            rwLock.readLock().unlock();
        }
    }
    
    public ArrayList<Item> viewAllItem() throws RemoteException, ClassNotFoundException, SQLException {
        try {
            rwLock.readLock().lock();
            Connection conn = DBConnection.getConnection();

            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery("select * from item");
            ArrayList<Item> customerList = new ArrayList<>();
            while (rst.next()) {
               Item item=new Item(rst.getString("code"),rst.getString("description"),rst.getString("unitPrice"),rst.getString("qtyOnHand")) ;
               customerList.add(item);
            }
           // System.out.println("customerList : "+customerList);
            return customerList;
        } finally {
            rwLock.readLock().unlock();
        }
    }
    
 /*   public boolean deleteCustomer(String id)throws RemoteException, ClassNotFoundException, SQLException {
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
    }*/

    public boolean updateItemQty(String code,int qty) throws ClassNotFoundException, SQLException{
        try{
            rwLock.writeLock().lock();
            String sql="update item set qtyOnHand=? where code=?";
            Connection conn=DBConnection.getConnection();
            PreparedStatement stm=conn.prepareStatement(sql);
            stm.setObject(1, qty);
            stm.setObject(2, code);
            return stm.executeUpdate()>0;
        }finally{
            rwLock.writeLock().unlock();
        }
    }
}
