/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.sales.Model_VehicleSalesProposal_Labor;

/**
 *
 * @author Arsiela
 */
public class Validator_VehicleSalesProposal_Labor implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_VehicleSalesProposal_Labor poEntity;

    Validator_VehicleSalesProposal_Labor(Object foValue){
        poEntity = (Model_VehicleSalesProposal_Labor) foValue;
    }

    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        if(poEntity.getTransNo()== null) {
            psMessage = "Transaction No is not set.";
            return false;
        } else {
            if (poEntity.getTransNo().isEmpty()){
                psMessage = "Transaction No is not set.";
                return false;
            }
        }
        
        if(poEntity.getLaborCde()== null) {
            psMessage = "labor Code is not set.";
            return false;
        } else {
            if (poEntity.getLaborCde().trim().isEmpty()){
                psMessage = "labor Code is not set.";
                return false;
            }
        }
        
        if(poEntity.getLaborDsc()== null) {
            psMessage = "Labor Description is not set.";
            return false;
        } else {
            if (poEntity.getLaborDsc().trim().isEmpty()){
                psMessage = "Labor Description is not set.";
                return false;
            }
        }
        
        if(poEntity.getChrgeTyp()== null) {
            psMessage = "Charge Type is not set.";
            return false;
        } else {
            if (poEntity.getChrgeTyp().trim().isEmpty()){
                psMessage = "Charge Type is not set.";
                return false;
            } 
        }
        //TODO
        if(poEntity.getChrgeTyp().equals("1")){
            if(poEntity.getLaborAmt()== null) {
                psMessage = "Labor Amount is not set.";
                return false;
            } else {
                if (poEntity.getLaborAmt() <= 0.00){
                    psMessage = "Labor Amount is not set.";
                    return false;
                }
            }
        }
        
        return true;
    }

    
    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
