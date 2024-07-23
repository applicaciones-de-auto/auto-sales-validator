/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.auto.model.sales.Model_Activity_Master;
import org.guanzon.auto.model.sales.Model_Activity_Member;

/**
 *
 * @author Arsiela
 */
public class Validator_Activity_Member implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Activity_Member poEntity;
    
    public Validator_Activity_Member(Object foValue){
        poEntity = (Model_Activity_Member) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
        
        if(poEntity.getEmployID()== null){
            psMessage = "Employee ID cannot be Empty.";
            return false;
        } else {
            if(poEntity.getEmployID().trim().isEmpty()){
                psMessage = "Employee ID cannot be Empty.";
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
