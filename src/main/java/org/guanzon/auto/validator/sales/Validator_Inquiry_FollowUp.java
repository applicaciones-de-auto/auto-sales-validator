/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.auto.model.sales.Model_Inquiry_FollowUp;

/**
 *
 * @author Arsiela
 */
public class Validator_Inquiry_FollowUp implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Inquiry_FollowUp poEntity;

    public Validator_Inquiry_FollowUp(Object foValue) {
        poEntity = (Model_Inquiry_FollowUp) foValue;
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
        
        if(poEntity.getReferNo()== null) {
            psMessage = "Referrence No is not set.";
            return false;
        } else {
            if (poEntity.getReferNo().isEmpty()){
                psMessage = "Referrence No is not set.";
                return false;
            }
        }
        
        Date date = (Date) poEntity.getValue("dTransact");
        if(date == null){
            psMessage = "Invalid Transaction Date.";
            return false;
        } else {
            if("1900-01-01".equals(xsDateShort(date))){
                psMessage = "Invalid Transaction Date.";
                return false;
            }
        }
        
        date = (Date) poEntity.getValue("dFollowUp");
        if(date == null){
            psMessage = "Invalid Next Follow Up Date.";
            return false;
        } else {
            if("1900-01-01".equals(xsDateShort(date))){
                psMessage = "Invalid Next Follow Up Date.";
                return false;
            }
        }
        
        if(poEntity.getEmployID()== null) {
            psMessage = "Employee is not set.";
            return false;
        } else {
            if (poEntity.getEmployID().isEmpty()){
                psMessage = "Employee Status is not set.";
                return false;
            }
        }
        
        if(poEntity.getMethodCd().equals("2")){
            if(poEntity.getSclMedia()== null) {
                psMessage = "Social Media Platform is not set.";
                return false;
            } else {
                if (poEntity.getSclMedia().isEmpty()){
                    psMessage = "Social Media Platform is not set.";
                    return false;
                }
            }
        }
        
        if(poEntity.getMethodCd().equals("2")){
            if(poEntity.getSclMedia()== null) {
                psMessage = "Social Media Platform is not set.";
                return false;
            } else {
                if (poEntity.getSclMedia().isEmpty()){
                    psMessage = "Social Media Platform is not set.";
                    return false;
                }
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
