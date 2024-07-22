/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.auto.model.sales.Model_Activity_Master;

/**
 *
 * @author Arsiela
 * Date Created: 07-19-2024
 */
public class Validator_Activity_Master implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Activity_Master poEntity;

    Validator_Activity_Master(Object foValue){
        poEntity = (Model_Activity_Master) foValue;
    }
    
    @Override
    public void setGRider(GRider foValue) {
        poGRider = foValue;
    }

    @Override
    public boolean isEntryOkay() {
//        try {
            String lsSQL = "";

            if(poEntity.getActvtyID()== null) {
                psMessage = "Activity ID is not set.";
                return false;
            } else {
                if (poEntity.getActvtyID().isEmpty()){
                    psMessage = "Activity ID is not set.";
                    return false;
                }
            }
            
            if(poEntity.getActNo()== null) {
                psMessage = "Activity No is not set.";
                return false;
            } else {
                if (poEntity.getActNo().isEmpty()){
                    psMessage = "Activity No is not set.";
                    return false;
                }
            }
            
            if(poEntity.getActTitle() == null) {
                psMessage = "Activity Title is not set.";
                return false;
            } else {
                if (poEntity.getActTitle().trim().isEmpty()){
                    psMessage = "Activity Title is not set.";
                    return false;
                }
            }
            
            if(poEntity.getActDesc() == null) {
                psMessage = "Activity Description is not set.";
                return false;
            } else {
                if (poEntity.getActDesc().trim().isEmpty()){
                    psMessage = "Activity Description is not set.";
                    return false;
                }
            }
            
            if(poEntity.getActSrce() == null) {
                psMessage = "Activity Source is not set.";
                return false;
            } else {
                if (poEntity.getActSrce().trim().isEmpty()){
                    psMessage = "Activity Source is not set.";
                    return false;
                }
            }
            
            if(poEntity.getActSrce() == null) {
                psMessage = "Activity Source is not set.";
                return false;
            } else {
                if (poEntity.getActSrce().trim().isEmpty()){
                    psMessage = "Activity Source is not set.";
                    return false;
                }
            }
            
            if(poEntity.getActTypID() == null) {
                psMessage = "Activity Type ID is not set.";
                return false;
            } else {
                if (poEntity.getActTypID().trim().isEmpty()){
                    psMessage = "Activity Type ID is not set.";
                    return false;
                }
            }
            
            if(poEntity.getProvID() == null) {
                psMessage = "Activity Province is not set.";
                return false;
            } else {
                if (poEntity.getProvID().trim().isEmpty()){
                    psMessage = "Activity Province is not set.";
                    return false;
                }
            }
            
            if(poEntity.getLocation() == null) {
                psMessage = "Activity Location is not set.";
                return false;
            } else {
                if (poEntity.getLocation().trim().isEmpty()){
                    psMessage = "Activity Location is not set.";
                    return false;
                }
            }
            
            if(poEntity.getCompnyn() == null) {
                psMessage = "Activity Company is not set.";
                return false;
            } else {
                if (poEntity.getCompnyn().trim().isEmpty()){
                    psMessage = "Activity Company is not set.";
                    return false;
                }
            }
            
            if(poEntity.getTrgtClnt() == null) {
                psMessage = "Target Client is not set.";
                return false;
            } else {
                if (poEntity.getTrgtClnt() == 0){
                    psMessage = "Target Client is not set.";
                    return false;
                }
            }
            
            if(poEntity.getEmployID() == null) {
                psMessage = "Employee Incharge is not set.";
                return false;
            } else {
                if (poEntity.getEmployID().trim().isEmpty()){
                    psMessage = "Employee Incharge is not set.";
                    return false;
                }
            }
            
            if(poEntity.getDeptID() == null) {
                psMessage = "Department Incharge is not set.";
                return false;
            } else {
                if (poEntity.getDeptID().trim().isEmpty()){
                    psMessage = "Department Incharge is not set.";
                    return false;
                }
            }
            
            if(poEntity.getLogRemrk() == null) {
                psMessage = "Log Remarks is not set.";
                return false;
            } else {
                if (poEntity.getLogRemrk().trim().isEmpty()){
                    psMessage = "Log Remarks is not set.";
                    return false;
                }
            }
            
            if (poEntity.getValue("dDateFrom") == null){
                psMessage = "Invalid Starting Date.";
                return false;
            } 
            
            if (poEntity.getValue("dDateThru") == null){
                psMessage = "Invalid End Date.";
                return false;
            } 
            
            if (poEntity.getValue("dApproved") == null){
                psMessage = "Invalid Approval Date.";
                return false;
            } 
            
            String lsdate = "1900-01-01";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOld = null;
            try {
                // Parse the formatted date string into a Date object
                dateOld = sdf.parse(lsdate);
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }
            
            Date date = (Date) poEntity.getValue("dDateFrom");
            System.out.println(date);
            
            if(date == dateOld || String.valueOf(date).equals(String.valueOf(dateOld)) || String.valueOf(date).equals("Mon Jan 01 00:00:00 CST 1900")){
                psMessage = "Invalid Start Date.";
                return false;
            } 
            
            date = (Date) poEntity.getValue("dDateThru");
            if(date == dateOld || String.valueOf(date).equals(String.valueOf(dateOld)) || String.valueOf(date).equals("Mon Jan 01 00:00:00 CST 1900")){
                psMessage = "Invalid End Date.";
                return false;
            }
            
        
//        } catch (SQLException ex) {
//            Logger.getLogger(Validator_Activity_Master.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
}
