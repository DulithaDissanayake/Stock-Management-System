/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ijse.cmjd.sms.connector;

import edu.ijse.cmjd.sms.controller.CustomerController;
import edu.ijse.cmjd.sms.controller.ItemController;
import edu.ijse.cmjd.sms.controller.OrderController;
import edu.ijse.cmjd.sms.controller.RemoteFactory;
import edu.ijse.cmjd.sms.controller.TemporyItemController;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Dulitha Dissanayake
 */
public class ServerConnector {
    private static ServerConnector serverConnector;
    private CustomerController customerController;
    private ItemController itemController;
    private TemporyItemController tempItemController;
    private OrderController orderController;
    private RemoteFactory remoteFactory;
    
    
    private ServerConnector() throws NotBoundException, MalformedURLException, RemoteException{
        remoteFactory = (RemoteFactory) Naming.lookup("rmi://localhost:5052/SystemServer");
    }
    
    public static ServerConnector getServerConnector() throws NotBoundException, MalformedURLException, RemoteException{
        if (serverConnector==null) {
            serverConnector=new ServerConnector();
        }return serverConnector;
    }
    
    public CustomerController getCustomerController() throws RemoteException{
        if (customerController==null) {
            customerController=remoteFactory.getCustomerController();
        }return customerController;
    }
    
    public ItemController getItemController() throws RemoteException{
        if (itemController==null) {
            itemController=remoteFactory.getItemController();
        }return itemController;
    }
    
    public TemporyItemController getTemporyItemController() throws RemoteException{
        if (tempItemController==null) {
            tempItemController=remoteFactory.getTempItemController();
        }return tempItemController;
    }
    
    public OrderController getOrderController() throws RemoteException{
        if (orderController==null) {
            orderController=remoteFactory.getOrderController();
        }return orderController;
    }
    
    
}
