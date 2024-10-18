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
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.auto.model.sales.Model_Inquiry_Master;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Arsiela
 */
public class Validator_Inquiry_Master implements ValidatorInterface {
    GRider poGRider;
    String psMessage;
    
    Model_Inquiry_Master poEntity;

    Validator_Inquiry_Master(Object foValue){
        poEntity = (Model_Inquiry_Master) foValue;
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
        
        if(poEntity.getClientID() == null) {
            psMessage = "Inquiring Customer is not set.";
            return false;
        } else {
            if (poEntity.getClientID().trim().isEmpty()){
                psMessage = "Inquiring Customer is not set.";
                return false;
            }
        }
        if(poEntity.getEmployID() == null) {
            psMessage = "Sales Executive is not set.";
            return false;
        } else {
            if (poEntity.getEmployID().trim().isEmpty()){
                psMessage = "Sales Executive is not set.";
                return false;
            }
        }
        if(poEntity.getBranchCd()== null) {
            psMessage = "Branch is not set.";
            return false;
        } else {
            if (poEntity.getBranchCd().isEmpty()){
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
        if(poEntity.getVhclID() == null) {
            psMessage = "Vehicle Priority is not set.";
            return false;
        } else {
            if (poEntity.getVhclID().trim().isEmpty()){
                psMessage = "Vehicle Priority is not set.";
                return false;
            }
        }
        if(poEntity.getIntrstLv() == null) {
            psMessage = "Interest Level is not set.";
            return false;
        } else {
            if (poEntity.getIntrstLv().trim().isEmpty()){
                psMessage = "Interest Level is not set.";
                return false;
            }
        }
        if(poEntity.getSourceCD() == null) {
            psMessage = "Source Code is not set.";
            return false;
        } else {
            if (poEntity.getSourceCD().trim().isEmpty()){
                psMessage = "Source Code is not set.";
                return false;
            } else {
                switch(poEntity.getSourceCD()){
                    case "1": //INQUIRY TYPE : ONLINE INQUIRY
                        if(poEntity.getSourceNo() == null) {
                            psMessage = "Source No is not set.";
                            return false;
                        } else {
                            if (poEntity.getSourceNo().trim().isEmpty()){
                                psMessage = "Source No is not set.";
                                return false;
                            }
                        }
                        break;
                    case "3": //INQUIRY TYPE : REFERRAL
                        if(poEntity.getSalesAgn()== null) {
                            psMessage = "Sales Agent is not set.";
                            return false;
                        } else {
                            if (poEntity.getSalesAgn().trim().isEmpty()){
                                psMessage = "Sales Agent is not set.";
                                return false;
                            }
                        }
                        break;
                    case "4": //INQUIRY TYPE : SALES CALL
                    case "5": //INQUIRY TYPE : EVENT
                        if(poEntity.getActvtyID()== null) {
                            psMessage = "Activity is not set.";
                            return false;
                        } else {
                            if (poEntity.getActvtyID().trim().isEmpty()){
                                psMessage = "Activity is not set.";
                                return false;
                            }
                        }
                        break;
                }
            }
        }
        
        if(poEntity.getTranStat() == null) {
            psMessage = "Transaction Status is not set.";
            return false;
        } else {
            if (poEntity.getTranStat().trim().isEmpty()){
                psMessage = "Transaction Status is not set.";
                return false;
            }
        }
        if (poEntity.getValue("dTransact") == null){
            psMessage = "Invalid Inquiry Date.";
            return false;
        }
        if (poEntity.getValue("dTargetDt") == null){
            psMessage = "Invalid Target Date.";
            return false;
        }
        String lsdate = "1900-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOld = null;
        try {
            // Parse the formatted date string into a Date object
            dateOld = sdf.parse(lsdate);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(Validator_Inquiry_Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date date = (Date) poEntity.getValue("dTransact");
        System.out.println(date);
        if(date == null){
            psMessage = "Invalid Inquiry Date.";
            return false;
        } else {
            if("1900-01-01".equals(xsDateShort(date))){
                psMessage = "Invalid Inquiry Date.";
                return false;
            }
        }
        date = (Date) poEntity.getValue("dTargetDt");
        if(date == null){
            psMessage = "Invalid Target Date.";
            return false;
        } else {
            if("1900-01-01".equals(xsDateShort(date))){
                psMessage = "Invalid Target Date.";
                return false;
            }
        }
        
        if(!poEntity.getTranStat().equals("0")) {
            if(poEntity.getPayMode() == null) {
                psMessage = "Payment mode is not set.";
                return false;
            } else {
                if (poEntity.getPayMode().trim().isEmpty()){
                    psMessage = "Payment mode is not set.";
                    return false;
                }
            }
            
            if(poEntity.getCustGrp() == null) {
                psMessage = "Customer Type is not set.";
                return false;
            } else {
                if (poEntity.getCustGrp().trim().isEmpty()){
                    psMessage = "Customer Type is not set.";
                    return false;
                }
            }
        } 
        
        try {
            String lsID = "";
            String lsDesc = "";
            String lsStat = "";
            String lsEmpl = "";
            String lsSQL = "";
            String lsWhere = "";
            String lsStandardSets = "";
            String lsDays = "";
            String lsInqdate = "1900-01-01";
            String lsInqRsv = "";
            String lsLastUpDte = "";
            
            if(poEntity.getEditMode() == EditMode.ADDNEW){
                lsSQL =   " SELECT "                                                                       
                        + "    a.sTransNox "                                                               
                        + "  , a.sBranchCd "                                                               
                        + "  , a.dTransact "                                                               
                        + "  , a.sInqryIDx "                                                               
                        + "  , a.sEmployID "                                                               
                        + "  , a.sClientID "                                                               
                        + "  , a.sContctID "                                                               
                        + "  , a.sAgentIDx "                                                               
                        + "  , a.dTargetDt "                                                               
                        + "  , a.cTranStat "                                                               
                        + "  , CASE "
                        + "     WHEN a.cTranStat = '0' THEN 'FOR FOLLOW-UP'"                           
                        + " 	WHEN a.cTranStat = '1' THEN 'ON PROCESS' "                                   
                        + " 	WHEN a.cTranStat = '2' THEN 'LOST SALE'  "                                   
                        + " 	WHEN a.cTranStat = '3' THEN 'WITH VSP'   "                                   
                        + " 	WHEN a.cTranStat = '4' THEN 'SOLD'       "                                     
                        + " 	ELSE 'CANCELLED'  "                                                          
                        + "    END AS sTranStat "                                                          
                        + "  , b.sCompnyNm      "                                                          
                        + "  , b.cClientTp      "                                                          
                        + "  , IFNULL(CONCAT( IFNULL(CONCAT(d.sHouseNox,' ') , ''), "                      
                        + " 	IFNULL(CONCAT(d.sAddressx,' ') , ''),  "                                     
                        + " 	IFNULL(CONCAT(e.sBrgyName,' '), ''),   "                                     
                        + " 	IFNULL(CONCAT(f.sTownName, ', '),''),  "                                     
                        + " 	IFNULL(CONCAT(g.sProvName),'') )	, '') AS sAddressx "                       
                        + "  , l.sCompnyNm AS sSalesExe "                                                  
                        + "  , m.sCompnyNm AS sSalesAgn "                                                  
                        + "  , p.sBranchNm  "                                                              
                        + " FROM customer_inquiry a "                                                      
                        + " LEFT JOIN client_master b ON a.sClientID = b.sClientID "                       
                        + " LEFT JOIN client_address c ON c.sClientID = a.sClientID AND c.cPrimaryx = 1  " 
                        + " LEFT JOIN addresses d ON d.sAddrssID = c.sAddrssID "                           
                        + " LEFT JOIN barangay e ON e.sBrgyIDxx = d.sBrgyIDxx  "                           
                        + " LEFT JOIN towncity f ON f.sTownIDxx = d.sTownIDxx  "                           
                        + " LEFT JOIN province g ON g.sProvIDxx = f.sProvIDxx   "                           
                        + " LEFT JOIN client_master k ON k.sClientID = a.sContctID  "                       
                        + " LEFT JOIN ggc_isysdbf.client_master l ON l.sClientID = a.sEmployID "            
                        + " LEFT JOIN client_master m ON m.sClientID = a.sAgentIDx "                        
                        + " LEFT JOIN branch p ON p.sBranchCd = a.sBranchCd "                        
                        + " WHERE a.cTranStat <> '5' " ;
                
                /* 1.
                * Will not allow SE to create new inquiries unless all 'on-process' or 'pending' inquries (named after him/herself, for 'n' days) have been settled already.
                */
                lsWhere = MiscUtil.addCondition(lsSQL, " a.sEmployID = " + SQLUtil.toSQL(poEntity.getEmployID()) 
                                                        //+ " AND a.sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                        + " AND (a.cTranStat = '1' OR a.cTranStat = '3')"  //a.cTranStat = '0' OR 
                                                        );
                
                System.out.println("EXISTING ONPROCESS/WITH VSP INQUIRY WITH SAME SE CHECK: " + lsSQL);
                ResultSet loRS = poGRider.executeQuery(lsWhere);
                //Check for existing inquiry with same SE
                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sInqryIDx");
                        lsDesc = loRS.getString("sCompnyNm");
                        lsInqdate = xsDateShort(loRS.getDate("dTransact"));
                        lsLastUpDte = xsDateShort(loRS.getDate("dLastUpdt"));
                        lsStat = loRS.getString("sTranStat");
                    }
                    MiscUtil.close(loRS);
                    
                    lsStandardSets = "SELECT sValuexxx FROM xxxstandard_sets WHERE sDescript = 'onprocess_inqdays'";
                    System.out.println("CHECK STANDARD SETS FROM onprocess_inqdays : " + lsStandardSets);
                    loRS = poGRider.executeQuery(lsStandardSets);
                    //Check for existing inquiry with same SE
                    if (MiscUtil.RecordCount(loRS) > 0){
                         while(loRS.next()){
                             lsDays = loRS.getString("sValuexxx");
                         }

                         MiscUtil.close(loRS);

                         LocalDate ldteFrom = strToDate(lsLastUpDte);
                         LocalDate ldteThru =  strToDate(xsDateShort((Date) poEntity.getValue("dTransact")));
                         Period age = Period.between(ldteFrom, ldteThru);
                         if(age.getDays() > Integer.parseInt(lsDays)){
                            psMessage = "A floating inquiry dated " + lsInqdate + " was found with same Sales Executive."
                                        + "\nPlease update this one before creating a new inquiry record."
                                        + "\n\n<Inquiry ID : " + lsID + ">"
                                        + "\n<Inquiring Customer : " + lsDesc + ">"
                                        + "\n<Inquiry Status: " + lsStat + ">";
                            return false;
                         }
                    }else {
                        psMessage = "Notify System Administrator to config Standard set for `onprocess_inqdays`.";
                        return false;
                    }
                   
                } 
                
                /* 2.
                * Will not allow SE to create new inquiry for client with an existing for-followup, on process and with vsp inquiry with the same SE.
                */
                lsWhere = MiscUtil.addCondition(lsSQL, " a.sClientID = " + SQLUtil.toSQL(poEntity.getClientID())
                                                        + " AND a.sEmployID = " + SQLUtil.toSQL(poEntity.getEmployID()) 
                                                        + " AND a.sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                        + " AND (a.cTranStat = '0' OR a.cTranStat = '1' OR a.cTranStat = '3')"  
                                                        );
                
                System.out.println("EXISTING CUSTOMER WITH THE SAME SE CHECK: " + lsSQL);
                loRS = poGRider.executeQuery(lsWhere);
                //Check for existing inquiry with same SE
                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sInqryIDx");
                        lsInqdate = xsDateShort(loRS.getDate("dTransact"));
                        lsStat = loRS.getString("sTranStat");
                    }
                    MiscUtil.close(loRS);
                    psMessage = "An existing inquiry with the same customer."
                                + "\nPlease update this one instead of creating a new inquiry record."
                                + "\n\n<Inquiry ID : " + lsID + ">"
                                + "\n<Inquiry Date: " + lsInqdate + ">"
                                + "\n<Inquiry Status: " + lsStat + ">";
                    return false;
                    
//                    lsStandardSets = "SELECT sValuexxx FROM xxxstandard_sets WHERE sDescript = 'onprocess_inqdays'";
//                    System.out.println("CHECK STANDARD SETS FROM onprocess_inqdays : " + lsStandardSets);
//                    loRS = poGRider.executeQuery(lsStandardSets);
//                    //Check for existing inquiry with same SE
//                    if (MiscUtil.RecordCount(loRS) > 0){
//                         while(loRS.next()){
//                             lsDays = loRS.getString("sValuexxx");
//                         }
//
//                         MiscUtil.close(loRS);
//
//                         LocalDate ldteFrom = strToDate(lsInqdate);
//                         LocalDate ldteThru =  strToDate(xsDateShort((Date) poEntity.getValue("dTransact")));
//                         Period age = Period.between(ldteFrom, ldteThru);
//                         if(age.getDays() > Integer.parseInt(lsDays)){
//                            psMessage = "An existing inquiry with the same customer dated " + lsInqdate
//                                       + " was found with same Sales Executive.\nPlease update this one instead of creating a new inquiry record."
//                                       + "\n<Inquiry ID : " + lsID + ">";
//                            return false;
//                         }
//                    } else {
//                        psMessage = "Notify System Administrator to config Standard set for `onprocess_inqdays`.";
//                        return false;
//                    }
                   
                } 
                
                // 3.
                //if same cust code is to be used by another SE, validate the status of the existing inquiry (check reservations, etc). 
                //Allow to be re-used if status is for follow up and Lost/Cancelled  only
                lsWhere = MiscUtil.addCondition(lsSQL, " a.sClientID = " + SQLUtil.toSQL(poEntity.getClientID()) 
                                                        + " AND a.sEmployID <> " + SQLUtil.toSQL(poEntity.getEmployID()) 
                                                        + " AND a.sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                        + " AND (a.cTranStat = '1' OR a.cTranStat = '3')" 
                                                        );
                
                System.out.println("EXISTING INQUIRY ON PROCESS AND WITH VSP WITH SAME CUSTOMER CHECK: " + lsWhere);
                loRS = poGRider.executeQuery(lsWhere);
                //Check for existing inquiry with same SE
                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sInqryIDx");
                        lsInqdate = xsDateShort(loRS.getDate("dTransact"));
                        lsDesc = loRS.getString("sCompnyNm");
                        lsEmpl = loRS.getString("sSalesExe");
                        lsStat = loRS.getString("sTranStat");
                    }
                    MiscUtil.close(loRS);
                    
                    //Check for existing reservation
                    lsInqRsv = " SELECT "
                            + "   sTransNox "
                            + " , dTransact "
                            + " , sReferNox "
                            + " , sClientID "
                            + " , nAmountxx "
                            + " , sSourceNo "
                            + " FROM customer_inquiry_reservation"
                            + " WHERE cTranStat = '2' "
                            + " AND sSourceNo = " + SQLUtil.toSQL(lsID) ;
                    System.out.println("EXISTING INQUIRY RESERVATION CHECK: " + lsInqRsv);
                    loRS = poGRider.executeQuery(lsInqRsv);
                    if (MiscUtil.RecordCount(loRS) > 0){
                        MiscUtil.close(loRS);
                        psMessage = "Found an existing inquiry record for " + lsDesc.toUpperCase() + " with vehicle reservation."
                                    + "\n\n<Sales Executive: " + lsEmpl.toUpperCase() + ">"
                                    + "\n<Inquiry ID: " + lsID + ">"
                                    + "\n<Inquiry Date: " + lsInqdate + ">"
                                    + "\n<Inquiry Status: " + lsStat + ">";
                        return false;
                    }
                    
                    psMessage =  "Found an existing inquiry record for " + lsDesc.toUpperCase() + "."
                                + "\n\n<Sales Executive: " + lsEmpl.toUpperCase() + ">"
                                + "\n<Inquiry ID: " + lsID + ">"
                                + "\n<Inquiry Date: " + lsInqdate + ">"
                                + "\n<Inquiry Status: " + lsStat + ">";
                    return false;
                } 
                
                // 4. TODO
                //if same cust code is to be used by another SE, validate the status of the existing inquiry (check reservations, etc). 
                //Allow to be re-used if the allowable days to be re-used is meet
                lsWhere = MiscUtil.addCondition(lsSQL, " a.sClientID = " + SQLUtil.toSQL(poEntity.getClientID()) 
                                                        + " AND a.sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo())  
                                                        + " AND (a.cTranStat = '0')" 
                                                        );
                
                System.out.println("EXISTING FOR FOLLOW-UP INQUIRY WITH SAME CUSTOMER CHECK: " + lsWhere);
                loRS = poGRider.executeQuery(lsWhere);
                //Check for existing inquiry with same SE
                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sInqryIDx");
                        lsInqdate = xsDateShort(loRS.getDate("dTransact"));
                        lsDesc = loRS.getString("sCompnyNm");
                        lsEmpl = loRS.getString("sSalesExe");
                        lsStat = loRS.getString("sTranStat");
                    }
                    MiscUtil.close(loRS);
                    
                    lsStandardSets = "SELECT sValuexxx FROM xxxstandard_sets WHERE sDescript = 'onprocess_inqdays'";
                    System.out.println("CHECK STANDARD SETS FROM onprocess_inqdays : " + lsStandardSets);
                    loRS = poGRider.executeQuery(lsStandardSets);
                    //Check for existing inquiry with same SE
                    if (MiscUtil.RecordCount(loRS) > 0){
                         while(loRS.next()){
                             lsDays = loRS.getString("sValuexxx");
                         }

                         MiscUtil.close(loRS);

                         LocalDate ldteFrom = strToDate(lsInqdate);
                         LocalDate ldteThru =  strToDate(xsDateShort((Date) poEntity.getValue("dTransact")));
                         Period age = Period.between(ldteFrom, ldteThru);
                         if(age.getDays() > Integer.parseInt(lsDays)){
                            psMessage =  "Found an existing inquiry record for " + lsDesc.toUpperCase() + "."
                                + "\n\n<Sales Executive: " + lsEmpl.toUpperCase() + ">"
                                + "\n<Inquiry ID: " + lsID + ">"
                                + "\n<Inquiry Date: " + lsInqdate + ">"
                                + "\n<Inquiry Status: " + lsStat + ">";
                            return false;
                         }
                    }else {
                        psMessage = "Notify System Administrator to config Standard set for `onprocess_inqdays`.";
                        return false;
                    }
                    
                }
                
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Validator_Inquiry_Master.class.getName()).log(Level.SEVERE, null, ex);
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


/**
 * 
                
                lsWhere = MiscUtil.addCondition(lsSQL, " a.sClientID = " + SQLUtil.toSQL(poEntity.getClientID()) 
                                                        + " AND a.sEmployID = " + SQLUtil.toSQL(poEntity.getEmployID()) 
                                                        + " AND (a.cTranStat = '0' OR a.cTranStat = '3' OR a.cTranStat = '1')" 
                                                        );
                System.out.println("EXISTING INQUIRY FOR-FOLLOWUP, ON PROCESS, VSP WITH THE SAME SE CHECK: " + lsSQL);
                loRS = poGRider.executeQuery(lsWhere);
                //Check for existing inquiry with same SE
                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sTransNox");
                        lsDesc = loRS.getString("sCompnyNm");
                    }
                    
                    MiscUtil.close(loRS);
                    psMessage = "Found an existing inquiry record for\n" + lsDesc.toUpperCase() + " <Inquiry ID:" + lsID + ">";
                    return false;
                } 
                
 * 
 */