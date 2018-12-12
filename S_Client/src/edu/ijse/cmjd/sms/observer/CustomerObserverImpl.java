/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ijse.cmjd.sms.observer;

//import edu.ijse.cmjd.sms.view.CustomerChart;
import edu.ijse.cmjd.sms.view.CustomerChart;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Dulitha Dissanayake
 */
public class CustomerObserverImpl extends UnicastRemoteObject implements CustomerObserver{
    CustomerChart viewAll;
    
    public CustomerObserverImpl(CustomerChart viewAll) throws RemoteException{
        this.viewAll=viewAll;
    }

    @Override
    public void update(String message) throws RemoteException {
        viewAll.showMessage(message);
    }
    
}
