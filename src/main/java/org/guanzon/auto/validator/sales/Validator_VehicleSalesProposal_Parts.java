/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import java.math.BigDecimal;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.sales.Model_VehicleSalesProposal_Parts;

/**
 *
 * @author Arsiela
 */
public class Validator_VehicleSalesProposal_Parts implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_VehicleSalesProposal_Parts poEntity;

    Validator_VehicleSalesProposal_Parts(Object foValue){
        poEntity = (Model_VehicleSalesProposal_Parts) foValue;
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
        
        if(poEntity.getDescript()== null) {
            psMessage = "Parts Description is not set.";
            return false;
        } else {
            if (poEntity.getDescript().trim().isEmpty()){
                psMessage = "Parts Description is not set.";
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
        
        if(poEntity.getQuantity() == null) {
            psMessage = "Quantity is not set.";
            return false;
        } else {
            if (poEntity.getQuantity() <= 0){
                psMessage = "Quantity is not set.";
                return false;
            } 
        }
        
        if(poEntity.getUnitPrce()== null) {
            psMessage = "Parts Amount is not set.";
            return false;
        } else {
            BigDecimal ldblAmt = new BigDecimal("0.00");
            BigDecimal ldblDsc = new BigDecimal("0.00");
            //ldblAmt = new BigDecimal(String.valueOf(poEntity.getSelPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
            ldblAmt = new BigDecimal(String.valueOf(poEntity.getNtPrtAmt().setScale(2, BigDecimal.ROUND_HALF_UP)));
            ldblDsc = new BigDecimal(String.valueOf(poEntity.getPartsDscount().setScale(2, BigDecimal.ROUND_HALF_UP)));
            if(poEntity.getChrgeTyp().equals("0")){
//                if (!ldblAmt.equals(ldblDsc)) {
                //if (ldblAmt != ldblDsc) {
                if (ldblAmt.compareTo(BigDecimal.ZERO) != 0) {
                    psMessage = "Invalid Net Parts Amount for parts " + poEntity.getDescript()+ ". Charge type is FOC.";
                    return false;
                }
            } else {
                //if (ldblAmt == ldblDsc) {
                if (ldblAmt.compareTo(BigDecimal.ZERO) == 0) {
                    psMessage = "Invalid Parts Amount for parts " + poEntity.getDescript() + ". Charge type is not FOC.";
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
