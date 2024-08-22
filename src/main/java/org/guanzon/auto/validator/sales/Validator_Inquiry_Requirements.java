/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.sales.Model_Inquiry_Requirements;

/**
 *
 * @author Arsiela
 */
public class Validator_Inquiry_Requirements implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Inquiry_Requirements poEntity;

    public Validator_Inquiry_Requirements(Object foValue) {
        poEntity = (Model_Inquiry_Requirements) foValue;
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
        
        if(poEntity.getRqrmtCde()== null) {
            psMessage = "Requirement Code is not set.";
            return false;
        } else {
            if (poEntity.getRqrmtCde().isEmpty()){
                psMessage = "Requirement Code is not set.";
                return false;
            }
        }
        
        Date date = (Date) poEntity.getValue("dReceived");
            if(date == null){
                psMessage = "Invalid Receive Date.";
                return false;
            } else {
                if("1900-01-01".equals(xsDateShort(date))){
                    psMessage = "Invalid Receive Date.";
                    return false;
                }
            }
        
        return true;
    }
    
    private static String xsDateShort(Date fdValue) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(fdValue);
        return date;
    }

    private static String xsDateShort(String fsValue) throws org.json.simple.parser.ParseException, java.text.ParseException {
        SimpleDateFormat fromUser = new SimpleDateFormat("MMMM dd, yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String lsResult = "";
        lsResult = myFormat.format(fromUser.parse(fsValue));
        return lsResult;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
}
