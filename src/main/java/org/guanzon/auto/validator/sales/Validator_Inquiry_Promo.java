/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.sales.Model_Inquiry_Promo;

/**
 *
 * @author Arsiela
 */
public class Validator_Inquiry_Promo implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Inquiry_Promo poEntity;

    public Validator_Inquiry_Promo(Object foValue) {
        poEntity = (Model_Inquiry_Promo) foValue;
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
        
        if(poEntity.getPromoID()== null) {
            psMessage = "Promo ID is not set.";
            return false;
        } else {
            if (poEntity.getPromoID().isEmpty()){
                psMessage = "Promo ID is not set.";
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
