/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.sales.Model_Inquiry_VehiclePriority;

/**
 *
 * @author Arsiela
 */
public class Validator_Inquiry_Vehicle_Priority implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Inquiry_VehiclePriority poEntity;

    Validator_Inquiry_Vehicle_Priority(Object foValue){
        poEntity = (Model_Inquiry_VehiclePriority) foValue;
    }

    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        if(poEntity.getTransNo()== null) {
            psMessage = "Inquiry Transaction No is not set.";
            return false;
        } else {
            if (poEntity.getTransNo().isEmpty()){
                psMessage = "Inquiry Transaction No is not set.";
                return false;
            }
        }
        
        if(poEntity.getVhclID()== null) {
            psMessage = "Vehicle ID is not set.";
            return false;
        } else {
            if (poEntity.getVhclID().isEmpty()){
                psMessage = "Vehicle ID is not set.";
                return false;
            }
        }
        
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
