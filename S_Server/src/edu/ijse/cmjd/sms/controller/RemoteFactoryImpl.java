/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ijse.cmjd.sms.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Dulitha Dissanayake
 */
public class RemoteFactoryImpl extends UnicastRemoteObject implements RemoteFactory{
    
    public RemoteFactoryImpl() throws RemoteException{
        
    }

    @Override
    public CustomerController getCustomerController() throws RemoteException {
        return new CustomerControllerImpl();
    }

    @Override
    public ItemController getItemController() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderController getOrderController() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TemporyItemController getTempItemController() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
