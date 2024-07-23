/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.sales.Model_Activity_Vehicle;

/**
 *
 * @author Arsiela
 */
public class Validator_Activity_Vehicle implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Activity_Vehicle poEntity;
    
    public Validator_Activity_Vehicle(Object foValue){
        poEntity = (Model_Activity_Vehicle) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        
        if(poEntity.getSerialID()== null){
            psMessage = "Serial ID cannot be Empty.";
            return false;
        } else {
            if(poEntity.getSerialID().trim().isEmpty()){
                psMessage = "Serial ID cannot be Empty.";
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
