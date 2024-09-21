/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.auto.model.sales.Model_VehicleDeliveryReceipt_Master;

/**
 *
 * @author Arsiela
 */
public class Validator_VehicleDeliveryReceipt_Master implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_VehicleDeliveryReceipt_Master poEntity;

    public Validator_VehicleDeliveryReceipt_Master(Object foValue) {
        poEntity = (Model_VehicleDeliveryReceipt_Master) foValue;
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
            psMessage = "Reference No is not set.";
            return false;
        } else {
            if (poEntity.getReferNo().isEmpty()){
                psMessage = "Reference No is not set.";
                return false;
            }
        }
        
        Date date = (Date) poEntity.getValue("dTransact");
        if(date == null){
            psMessage = "Transaction Date is not set.";
            return false;
        } else {
            if("1900-01-01".equals(xsDateShort(date))){
                psMessage = "Transaction Date is not set.";
                return false;
            }
        }
        
        if(poEntity.getCustType()== null) {
            psMessage = "Customer Type is not set.";
            return false;
        } else {
            if (poEntity.getCustType().isEmpty()){
                psMessage = "Customer Type is not set.";
                return false;
            } else {
                if(poEntity.getCustType().equals("0")){
                    if(poEntity.getSourceNo()== null) {
                        psMessage = "VSP is not set.";
                        return false;
                    } else {
                        if (poEntity.getSourceNo().isEmpty()){
                            psMessage = "VSP is not set.";
                            return false;
                        }
                    }
                }
            }
        }
        
        if(poEntity.getClientID()== null) {
            psMessage = "Client is not set.";
            return false;
        } else {
            if (poEntity.getClientID().isEmpty()){
                psMessage = "Client is not set.";
                return false;
            }
        }
        
        if(poEntity.getSerialID()== null) {
            psMessage = "Vehicle Serial is not set.";
            return false;
        } else {
            if (poEntity.getSerialID().isEmpty()){
                psMessage = "Vehicle Serial is not set.";
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
        //Validate cancellation
        if(poEntity.getTranStat().equals(TransactionStatus.STATE_CANCELLED)){
            
        }
        
        try {
            String lsID = "";
            String lsSQL = poEntity.getSQL();
            //Validate exisitng DR of VSP
            if(poEntity.getCustType().equals("0")){
                lsSQL = MiscUtil.addCondition(lsSQL, " a.sSourceNo = " + SQLUtil.toSQL(poEntity.getSourceNo())
                                                        +" AND a.sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo()))
                                                        +" AND a.cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED) ;
                System.out.println("EXISTING VDR FOR VSP CHECK: " + lsSQL);
                ResultSet loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                        while(loRS.next()){
                            lsID = loRS.getString("sReferNox");
                        }

                        MiscUtil.close(loRS);
                        psMessage = "Found existing DR for VSP.\n\nSaving aborted." ;
                        return false;
                } 
            }
            
//            if(poEntity.getCancelld() != null){
//                if(poEntity.getCancelld().trim().isEmpty()){
//                    lsID = "";
//                    lsSQL = poEntity.getSQL();
//
//                    lsSQL = MiscUtil.addCondition(lsSQL, "  (a.sCancelld = '' OR a.sCancelld = NULL) "
//                                                            + " AND a.sBrBankID = " + SQLUtil.toSQL(poEntity.getBrBankID())
//                                                            + " AND a.cPayModex = " + SQLUtil.toSQL(poEntity.getPayMode())
//                                                            + " AND a.sSourceNo = " + SQLUtil.toSQL(poEntity.getSourceNo())
//                                                            +" AND a.sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo())) ;
//                    System.out.println("EXISTING BANK APPLICATION CHECK: " + lsSQL);
//                    loRS = poGRider.executeQuery(lsSQL);
//
//                    if (MiscUtil.RecordCount(loRS) > 0){
//                            while(loRS.next()){
//                                lsID = loRS.getString("sApplicNo");
//                            }
//
//                            MiscUtil.close(loRS);
//                            psMessage = "Existing Bank Application for "+poEntity.getBankName()+". Saving aborted." ;
//                            return false;
//                    }  
//                }
//            }
        } catch (SQLException ex) {
            Logger.getLogger(Validator_VehicleDeliveryReceipt_Master.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return true;
    }
    
    /*Convert Date to String*/
    private LocalDate strToDate(String val) {
        DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(val, date_formatter);
        return localDate;
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
