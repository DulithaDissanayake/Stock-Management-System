/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ijse.cmjd.sms.reservation;

import edu.ijse.cmjd.sms.controller.ItemController;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dulitha Dissanayake
 */
public class ItemReserver {
    private Map<String,ItemController> reserveMap=new HashMap<>();
    
    public boolean reserveItem(String id,ItemController itemController){
        if (reserveMap.containsKey(id)) {
            if (reserveMap.get(id)==itemController) {
                return true;
            }else{
                return false;
            }
        }else{
            reserveMap.put(id, itemController);
            return true;
        }
    }
    
    
    public boolean releaseItem(String id,ItemController itemController){
        if (reserveMap.get(id)==itemController) {
            reserveMap.remove(id);
            return true;
        }else{
            return false;
        }
    }
}
