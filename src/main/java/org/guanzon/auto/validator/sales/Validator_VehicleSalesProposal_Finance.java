/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.sales.Model_VehicleSalesProposal_Finance;

/**
 *
 * @author Arsiela
 */
public class Validator_VehicleSalesProposal_Finance implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_VehicleSalesProposal_Finance poEntity;

    Validator_VehicleSalesProposal_Finance(Object foValue){
        poEntity = (Model_VehicleSalesProposal_Finance) foValue;
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
        
        if(poEntity.getFinPromo()== null) {
            psMessage = "Finance Promo is not set.";
            return false;
        } else {
            if (poEntity.getFinPromo().trim().isEmpty()){
                psMessage = "Finance Promo is not set.";
                return false;
            }
        }
        
        if(poEntity.getBankID()== null) {
            psMessage = "Bank ID is not set.";
            return false;
        } else {
            if (poEntity.getBankID().trim().isEmpty()){
                psMessage = "Bank ID is not set.";
                return false;
            }
        }
        
        if(poEntity.getBankname()== null) {
            psMessage = "Bank Name is not set.";
            return false;
        } else {
            if (poEntity.getBankname().isEmpty()){
                psMessage = "Bank Name is not set.";
                return false;
            }
        }
        
        if(poEntity.getFinAmt() == null) {
            psMessage = "Finance Amount is not set.";
            return false;
        } else {
            if (poEntity.getFinAmt() <= 0.00){
                psMessage = "Finance Amount is not set.";
                return false;
            }
        }
        
        if(poEntity.getAcctTerm() == null) {
            psMessage = "Finance Term is not set.";
            return false;
        } else {
            if (poEntity.getAcctTerm() <= 0.00){
                psMessage = "Finance Term is not set.";
                return false;
            }
        }
        
        if(poEntity.getAcctRate() == null) {
            psMessage = "Finance Rate is not set.";
            return false;
        } else {
            if (poEntity.getAcctRate() <= 0.00){
                psMessage = "Finance Rate is not set.";
                return false;
            }
        }
        
//        if(poEntity.getRebates() == null) {
//            psMessage = "Finance Rebate is not set.";
//            return false;
//        } else {
//            if (poEntity.getRebates() <= 0.00){
//                psMessage = "Finance Rebate is not set.";
//                return false;
//            }
//        }
        
        if(poEntity.getMonAmort() == null) {
            psMessage = "Finance Amortization is not set.";
            return false;
        } else {
            if (poEntity.getMonAmort() <= 0.00){
                psMessage = "Finance Amortization is not set.";
                return false;
            }
        }
        
//        if(poEntity.getPNValue() == null) {
//            psMessage = "Finance Value is not set.";
//            return false;
//        } else {
//            if (poEntity.getPNValue() <= 0.00){
//                psMessage = "Finance Value is not set.";
//                return false;
//            }
//        }
//        
//        if(poEntity.getBnkPaid() == null) {
//            psMessage = "Finance Bank Pay is not set.";
//            return false;
//        } else {
//            if (poEntity.getBnkPaid() <= 0.00){
//                psMessage = "Finance Bank Pay is not set.";
//                return false;
//            }
//        }
        
        if(poEntity.getGrsMonth() == null) {
            psMessage = "Finance Gross Amount is not set.";
            return false;
        } else {
            if (poEntity.getGrsMonth() <= 0.00){
                psMessage = "Finance Gross Amount is not set.";
                return false;
            }
        }
        
        if(poEntity.getNtDwnPmt() == null) {
            psMessage = "Finance Net Downpayment is not set.";
            return false;
        } else {
            if (poEntity.getNtDwnPmt() <= 0.00){
                psMessage = "Finance Net Downpayment is not set.";
                return false;
            }
        }
        
//        if(poEntity.getDiscount() == null) {
//            psMessage = "Finance Discount is not set.";
//            return false;
//        } else {
//            if (poEntity.getDiscount() <= 0.00){
//                psMessage = "Finance Discount is not set.";
//                return false;
//            }
//        }
        
        return true;
    }

    
    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
