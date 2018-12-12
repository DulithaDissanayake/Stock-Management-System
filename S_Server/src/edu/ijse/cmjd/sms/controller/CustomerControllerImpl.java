/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ijse.cmjd.sms.controller;

import edu.ijse.cmjd.sms.dbaccess.CustomerDBAccess;
import edu.ijse.cmjd.sms.model.Customer;
import edu.ijse.cmjd.sms.observable.CustomerObservable;
import edu.ijse.cmjd.sms.observer.CustomerObserver;
import edu.ijse.cmjd.sms.reservation.CustomerReserver;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dulitha Dissanayake
 */
public class CustomerControllerImpl extends UnicastRemoteObject implements CustomerController {

    private static CustomerObservable customerObservable = new CustomerObservable();
    private static CustomerReserver customerReserver = new CustomerReserver();
    private CustomerDBAccess dbAccess = new CustomerDBAccess();

    public CustomerControllerImpl() throws RemoteException {

    }

    @Override
    public boolean addCustomer(Customer customer) throws RemoteException, ClassNotFoundException, SQLException {
        boolean isAdded=dbAccess.addCustomer(customer);
        final String id=customer.getId();
        if (isAdded) {
            new Thread(){
                public void run(){
                    try {
                        customerObservable.setId(id);
                    } catch (RemoteException ex) {
                        Logger.getLogger(CustomerControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(String id) throws RemoteException, ClassNotFoundException, SQLException {
        if (customerReserver.reserveCustomer(id, this)) {
            boolean isDeleted = dbAccess.deleteCustomer(id);
            if (isDeleted) {
                new Thread() {
                    public void run() {
                        try {
                            customerObservable.notifyObserver("The Customer " + id + " was Deleted");
                        } catch (RemoteException ex) {
                            Logger.getLogger(CustomerControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }.start();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) throws RemoteException, ClassNotFoundException, SQLException {
        if (customerReserver.reserveCustomer(customer.getId(), this)) {
            boolean isUpdated = dbAccess.updateCustomer(customer);
            if (isUpdated) {
                new Thread() {
                    public void run() {
                        try {
                            customerObservable.notifyObserver("Updated the Customer " + customer.getName() + " , " + customer.getAddress());
                        } catch (RemoteException ex) {
                            Logger.getLogger(CustomerControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }.start();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Customer searchCustomer(String id) throws RemoteException, ClassNotFoundException, SQLException {
        return dbAccess.searchCustomer(id);
    }

    @Override
    public ArrayList<Customer> viewAllCustomer() throws RemoteException, ClassNotFoundException, SQLException {
        return dbAccess.viewAllCustomer();
    }

    @Override
    public boolean reserveCustomer(String id) throws RemoteException {
        return customerReserver.reserveCustomer(id, this);
    }

    @Override
    public boolean releaseCustomer(String id) throws RemoteException {
        return customerReserver.releaseCustomer(id, this);
    }

    @Override
    public void addCustomerObserver(CustomerObserver customerObserver) throws RemoteException {
        customerObservable.addCustomerObserver(customerObserver);
        
    }

    @Override
    public void removeCustomerObserver(CustomerObserver customerObserver) throws RemoteException {
        customerObservable.removeCustomerObserver(customerObserver);
    }

}
