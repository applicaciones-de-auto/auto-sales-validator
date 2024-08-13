/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.auto.model.sales.Model_Bank_Application;

/**
 *
 * @author Arsiela
 */
public class Validator_Inquiry_BankApplication implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Bank_Application poEntity;

    public Validator_Inquiry_BankApplication(Object foValue) {
        poEntity = (Model_Bank_Application) foValue;
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
        
        if(poEntity.getApplicNo()== null) {
            psMessage = "Application No is not set.";
            return false;
        } else {
            if (poEntity.getApplicNo().isEmpty()){
                psMessage = "Application No is not set.";
                return false;
            }
        }
        
        Date date = (Date) poEntity.getValue("dAppliedx");
        if(date == null){
            psMessage = "Invalid Applied Date.";
            return false;
        } else {
            if("1900-01-01".equals(xsDateShort(date))){
                psMessage = "Invalid Applied Date.";
                return false;
            }
        }
        
        if(poEntity.getTranStat()== null) {
            psMessage = "Application Status is not set.";
            return false;
        } else {
            if (poEntity.getTranStat().isEmpty()){
                psMessage = "Application Status is not set.";
                return false;
            }
        }
        
        if(poEntity.getTranStat().equals("2")){
            date = (Date) poEntity.getValue("dApproved");
            if(date == null){
                psMessage = "Invalid Approve Date.";
                return false;
            } else {
                if("1900-01-01".equals(xsDateShort(date))){
                    psMessage = "Invalid Approve Date.";
                    return false;
                }
            }
        }
        
        try {
            String lsID = "";
            String lsSQL = poEntity.getSQL();

            if(poEntity.getEditMode() == EditMode.ADDNEW){
                lsSQL = MiscUtil.addCondition(lsSQL, " a.sApplicNo = " + SQLUtil.toSQL(poEntity.getApplicNo())) ;
                System.out.println("EXISTING BANK APPLICATION CHECK: " + lsSQL);
                ResultSet loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                        while(loRS.next()){
                            lsID = loRS.getString("sApplicNo");
                        }

                        MiscUtil.close(loRS);
                        psMessage = "Existing Bank Application Number. Saving aborted." ;
                        return false;
                }      
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Inquiry_BankApplication.class.getName()).log(Level.SEVERE, null, ex);
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
