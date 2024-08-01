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
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
        try {

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
            
            if(poEntity.getLocation() == null) {
                psMessage = "Activity Location is not set.";
                return false;
            } else {
                if (poEntity.getLocation().trim().isEmpty()){
                    psMessage = "Activity Location is not set.";
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
            
            if(date == null){
                psMessage = "Invalid Start Date.";
                return false;
            } else {
                if("1900-01-01".equals(xsDateShort(date))){
                    psMessage = "Invalid Start Date.";
                    return false;
                }
            }
            
            date = (Date) poEntity.getValue("dDateThru");
            if(date == null){
                psMessage = "Invalid Start Date.";
                return false;
            } else {
                if("1900-01-01".equals(xsDateShort(date))){
                    psMessage = "Invalid End Date.";
                    return false;
                }
            }
            
            LocalDate ldteFrom = strToDate(xsDateShort((Date) poEntity.getValue("dDateFrom")));
            LocalDate ldteThru =  strToDate(xsDateShort((Date) poEntity.getValue("dDateThru")));
            Period age = Period.between(ldteFrom, ldteThru);
            if(age.getDays() < 0){
                psMessage = "Invalid Activity Date.";
                return false;
            }
            
//            if(date == dateOld || String.valueOf(date).equals(String.valueOf(dateOld)) || String.valueOf(date).equals("Mon Jan 01 00:00:00 CST 1900")){
//                psMessage = "Invalid Start Date.";
//                return false;
//            } 
//            
//            
//            if(date == dateOld || String.valueOf(date).equals(String.valueOf(dateOld)) || String.valueOf(date).equals("Mon Jan 01 00:00:00 CST 1900")){
//                psMessage = "Invalid End Date.";
//                return false;
//            }
            
            String lsID = "";
            String lsDesc  = "";
            String lsSQL =    "   SELECT "                                                  
                            + "   a.sActvtyID "                                             
                            + " , a.sActNoxxx "                                             
                            + " , a.sActTitle "                                             
                            + " , a.sActTypID "                                              
                            + " , a.dDateFrom "                                             
                            + " , a.dDateThru "                                             
                            + " , a.sLocation "                                             
                            + " , a.cTranStat "                                             
//                            + " , b.sBrgyIDxx "                                             
//                            + " , b.sTownIDxx "                                             
                            + "FROM activity_master a "   ;                                  
//                            + "LEFT JOIN activity_location b ON b.sTransNox = a.sActvtyID ";
            lsSQL = MiscUtil.addCondition(lsSQL, " REPLACE(a.sActTitle,' ','') = " + SQLUtil.toSQL(poEntity.getActTitle().replace(" ",""))) +
                                                    " AND a.sActTypID = " + SQLUtil.toSQL(poEntity.getActTypID()) +
                                                    " AND a.dDateFrom = " + SQLUtil.toSQL(xsDateShort((Date) poEntity.getValue("dDateFrom")))+
                                                    " AND a.dDateThru = " + SQLUtil.toSQL(xsDateShort((Date) poEntity.getValue("dDateThru")))+
                                                    " AND a.sLocation = " + SQLUtil.toSQL(poEntity.getLocation())+
                                                    " AND a.cTranStat = '1' " +
                                                    " AND a.sActvtyID <> " + SQLUtil.toSQL(poEntity.getActvtyID()) ;
            System.out.println("EXISTING ACTIVITY CHECK: " + lsSQL);
            ResultSet loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sActvtyID");
                        lsDesc = loRS.getString("sActTitle");
                    }
                    
                    MiscUtil.close(loRS);
                    psMessage = "Existing Activity Record.\n\nActivity ID: " + lsID + "\nActivity Title: " + lsDesc.toUpperCase();
                    return false;
            }
            
            
            //Cancellation Validation
            if(poEntity.getTranStat().equals("2")){
                /* check if already LINKED with OTHER FORMS */
                //Inquiry
                lsID = "";
                lsDesc = "";
                lsSQL =   " SELECT "                                                   
                        + "   a.sTransNox "                                            
                        + " , a.dTransact "                                            
                        + " , a.sActvtyID "                                            
                        + " FROM customer_inquiry a "                                  
                        + " LEFT JOIN activity_master b ON b.sActvtyID = a.sActvtyID ";
                lsSQL = MiscUtil.addCondition(lsSQL, " a.sActvtyID = " + SQLUtil.toSQL(poEntity.getActvtyID())) ;
                System.out.println("EXISTING ACTIVITY LINKED THRU INQUIRY CHECK: " + lsSQL);
                loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                        while(loRS.next()){
                            lsID = loRS.getString("sTransNox");
                            lsDesc = xsDateShort(loRS.getString("dTransact"));
                        }

                        MiscUtil.close(loRS);
                        psMessage = "Unable to cancel Activity. It has already been used for inquiry!\n\nInquiry ID: " + lsID + "\nInquiry Date:" + lsDesc ;
                        return false;
                }
                //JO / Diagnostic
                lsID = "";
                lsSQL =   " SELECT "                                                   
                        + "   a.sTransNox "                                            
                        + " , a.dTransact "                                            
                        + " , a.sActvtyID "                                            
                        + " FROM customer_inquiry a "                                  
                        + " LEFT JOIN diagnostic_master b ON b.sActvtyID = a.sActvtyID ";
                lsSQL = MiscUtil.addCondition(lsSQL, " a.sActvtyID = " + SQLUtil.toSQL(poEntity.getActvtyID())) ;
                System.out.println("EXISTING ACTIVITY LINKED THRU JOB ORDER CHECK: " + lsSQL);
                loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                        while(loRS.next()){
                            lsID = loRS.getString("sTransNox");
                            lsDesc = xsDateShort(loRS.getString("dTransact"));
                        }

                        MiscUtil.close(loRS);
                        psMessage = "Unable to cancel Activity. It has already been used for job order!\n\nJO NO: " + lsID + "\nJO Date:" + lsDesc ;
                        return false;
                }
                //PRS
                
                //Accounting
                
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Activity_Master.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(Validator_Activity_Master.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Validator_Activity_Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public String getMessage() {
        return psMessage;
    }
    
    public static String xsDateShort(Date fdValue) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(fdValue);
        return date;
    }

    public static String xsDateShort(String fsValue) throws org.json.simple.parser.ParseException, java.text.ParseException {
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
