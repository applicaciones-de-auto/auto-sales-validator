/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.sales.Model_Activity_Location;

/**
 *
 * @author Arsiela
 */
public class Validator_Activity_Location  implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Activity_Location poEntity;
    
    public Validator_Activity_Location(Object foValue){
        poEntity = (Model_Activity_Location) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        
        if(poEntity.getProvID() == null) {
            psMessage = "Activity Province is not set.";
            return false;
        } else {
            if (poEntity.getProvID().trim().isEmpty()){
                psMessage = "Activity Province is not set.";
                return false;
            }
        }
        
        if(poEntity.getTownID()== null){
            psMessage = "Town ID cannot be Empty.";
            return false;
        } else {
            if(poEntity.getTownID().trim().isEmpty()){
                psMessage = "Town ID cannot be Empty.";
                return false;
            }
        }
        
        if(poEntity.getBrgyID()== null){
            psMessage = "Barangay ID cannot be Empty.";
            return false;
        } else {
            if(poEntity.getBrgyID().trim().isEmpty()){
                psMessage = "Barangay ID cannot be Empty.";
                return false;
            }
        }
        
        if(poEntity.getEntryNo() == 0){
            psMessage = "Invalid Entry Number.";
            return false;
        }
        
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
}
