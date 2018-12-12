/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ijse.cmjd.sms.reservation;

import edu.ijse.cmjd.sms.controller.CustomerController;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dulitha Dissanayake
 */
public class CustomerReserver {
    private Map<String,CustomerController> reserveMap=new HashMap<>();
    
    public boolean reserveCustomer(String id,CustomerController customerController){
        if (reserveMap.containsKey(id)) {
            if (reserveMap.get(id)==customerController) {
                return true;
            }else{
                return false;
            }
        }reserveMap.put(id, customerController);
        return false;
    }
    
    
    public boolean releaseCustomer(String id,CustomerController customerController){
        if (reserveMap.get(id)==customerController) {
            reserveMap.remove(id);
            return true;
        }return false;
    }
    
}
