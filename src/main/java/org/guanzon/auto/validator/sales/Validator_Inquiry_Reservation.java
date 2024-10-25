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
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.auto.model.sales.Model_Inquiry_Reservation;

/**
 *
 * @author Arsiela
 */
public class Validator_Inquiry_Reservation implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Inquiry_Reservation poEntity;

    public Validator_Inquiry_Reservation(Object foValue) {
        poEntity = (Model_Inquiry_Reservation) foValue;
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
        
        if(poEntity.getSourceNo()== null) {
            psMessage = "Transaction Source No is not set.";
            return false;
        } else {
            if (poEntity.getSourceNo().isEmpty()){
                psMessage = "Transaction Source No is not set.";
                return false;
            }
        }
        
        if(poEntity.getSourceCD()== null) {
            psMessage = "Source Code is not set.";
            return false;
        } else {
            if (poEntity.getSourceCD().isEmpty()){
                psMessage = "Source Code is not set.";
                return false;
            }
        }
        
        if(poEntity.getAmount() == null) {
            psMessage = "Invalid reservation amount.";
            return false;
        } else {
            if (poEntity.getAmount() <= 0){
                psMessage = "Invalid reservation amount.";
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
