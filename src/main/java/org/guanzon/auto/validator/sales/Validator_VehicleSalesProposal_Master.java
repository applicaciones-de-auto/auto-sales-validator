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
import org.guanzon.auto.model.sales.Model_VehicleSalesProposal_Master;

/**
 *
 * @author Arsiela
 */
public class Validator_VehicleSalesProposal_Master implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_VehicleSalesProposal_Master poEntity;

    Validator_VehicleSalesProposal_Master(Object foValue){
        poEntity = (Model_VehicleSalesProposal_Master) foValue;
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
        
        if(poEntity.getInqryID()== null) {
            psMessage = "Inquiry is not set.";
            return false;
        } else {
            if (poEntity.getInqryID().trim().isEmpty()){
                psMessage = "Inquiry is not set.";
                return false;
            }
        }
        
        if(poEntity.getClientID() == null) {
            psMessage = "Buying Customer is not set.";
            return false;
        } else {
            if (poEntity.getClientID().trim().isEmpty()){
                psMessage = "Buying Customer is not set.";
                return false;
            }
        }
        
        if(poEntity.getBranchCD()== null) {
            psMessage = "Branch is not set.";
            return false;
        } else {
            if (poEntity.getBranchCD().isEmpty()){
                psMessage = "Branch is not set.";
                return false;
            }
        }
        
        if(poEntity.getIsVhclNw() == null) {
            psMessage = "Vehicle Category is not set.";
            return false;
        } else {
            if (poEntity.getIsVhclNw().trim().isEmpty()){
                psMessage = "Vehicle Category is not set.";
                return false;
            }
        }
        
        if(poEntity.getUnitPrce() != null || poEntity.getUnitPrce() > 0.00){
            if(poEntity.getSerialID()== null) {
                psMessage = "Vehicle Serial is not set.";
                return false;
            } else {
                if (poEntity.getSerialID().trim().isEmpty()){
                    psMessage = "Vehicle Serial is not set.";
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
    
    /*Convert Date to String*/
    private LocalDate strToDate(String val) {
        DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(val, date_formatter);
        return localDate;
    }
    
}
