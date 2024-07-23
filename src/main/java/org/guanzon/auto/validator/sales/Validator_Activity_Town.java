/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.sales.Model_Activity_Town;

/**
 *
 * @author Arsiela
 */
public class Validator_Activity_Town  implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Activity_Town poEntity;
    
    public Validator_Activity_Town(Object foValue){
        poEntity = (Model_Activity_Town) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        
        if(poEntity.getTownID()== null){
            psMessage = "Town ID cannot be Empty.";
            return false;
        } else {
            if(poEntity.getTownID().trim().isEmpty()){
                psMessage = "Town ID cannot be Empty.";
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
