/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ijse.cmjd.sms.main;

import edu.ijse.cmjd.sms.controller.RemoteFactoryImpl;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dulitha Dissanayake
 */
public class ServerStart {
    public static void main(String[] args) {
         try {
            Registry thogaKadeRegistry = LocateRegistry.createRegistry(5052);
            System.out.println("Server is Starting..");
            thogaKadeRegistry.rebind("SystemServer", new RemoteFactoryImpl());
        } catch (RemoteException ex) {
            Logger.getLogger(ServerStart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
