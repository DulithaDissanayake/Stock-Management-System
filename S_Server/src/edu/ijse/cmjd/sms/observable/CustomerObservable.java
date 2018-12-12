/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ijse.cmjd.sms.observable;

import edu.ijse.cmjd.sms.observer.CustomerObserver;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Dulitha Dissanayake
 */
public class CustomerObservable {
    private String id;
    private String message;
    private ArrayList<CustomerObserver> observerList=new ArrayList<>();
    
    public void addCustomerObserver(CustomerObserver customerObserver){
        observerList.add(customerObserver);
    }
    
    public void removeCustomerObserver(CustomerObserver customerObserver){
        observerList.remove(customerObserver);
    }
    
    public void notifyObserver(String message) throws RemoteException{
        this.message=message;
        for (CustomerObserver customerObserver : observerList) {
            customerObserver.update(message);
        }
    }
    
    public void setId(String id) throws RemoteException{
        if (this.id !=id) {
            this.id=id;
            notifyObserver("A new Customer wad added "+id);
        }
    }
    
}
