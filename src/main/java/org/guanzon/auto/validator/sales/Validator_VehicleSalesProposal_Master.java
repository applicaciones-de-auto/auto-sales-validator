/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.TransactionStatus;
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
            psMessage = "Transaction No is not set.";
            return false;
        } else {
            if (poEntity.getTransNo().isEmpty()){
                psMessage = "Transaction No is not set.";
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
        
        if(poEntity.getPayMode() == null) {
            psMessage = "Payment mode is not set.";
            return false;
        } else {
            if (poEntity.getPayMode().trim().isEmpty()){
                psMessage = "Payment mode is not set.";
                return false;
            }
        }
        
//        if(poEntity.getUnitPrce().compareTo((new BigDecimal("0.00"))) > 0){
//            if(poEntity.getSerialID()== null) {
//                psMessage = "Vehicle Serial is not set.";
//                return false;
//            } else {
//                if (poEntity.getSerialID().trim().isEmpty()){
//                    psMessage = "Vehicle Serial is not set.";
//                    return false;
//                }
//            }
//        }
        
//        Date date = (Date) poEntity.getDelvryDt();
//        if(date == null){
//            psMessage = "Invalid Delivery Date.";
//            return false;
//        } else {
//            if("1900-01-01".equals(xsDateShort(date))){
//                psMessage = "Invalid Delivery Date.";
//                return false;
//            }
//        }
//        LocalDate ldDlvryDte = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
//        LocalDate ldTranDte = LocalDate.of(poEntity.getTransactDte().getYear(), poEntity.getTransactDte().getMonth(), poEntity.getTransactDte().getDay());
//        if (ldDlvryDte.isBefore(ldTranDte)) {
//                psMessage = "Delivery Date cannot be before the transaction date.";
//                return false;
//        

        Date date = (Date) poEntity.getDelvryDt();
        if (date == null) {
            psMessage = "Invalid Delivery Date.";
            return false;
        } else {
            if ("1900-01-01".equals(xsDateShort(date))) {
                psMessage = "Invalid Delivery Date.";
                return false;
            }
        }
        
        // Convert java.util.Date to java.time.LocalDate
        LocalDate ldDlvryDte = Instant.ofEpochMilli(date.getTime())
                                      .atZone(ZoneId.systemDefault())
                                      .toLocalDate();

        // Convert transaction date to LocalDate
        Date transactDate = poEntity.getTransactDte();
        LocalDate ldTranDte = Instant.ofEpochMilli(transactDate.getTime())
                                     .atZone(ZoneId.systemDefault())
                                     .toLocalDate();

        // Check if delivery date is before transaction date
        if (ldDlvryDte.isBefore(ldTranDte)) {
            psMessage = "Delivery Date cannot be before the transaction date.";
            return false;
        }
        
        if (poEntity.getTranTotl().equals(new BigDecimal("0.00")) || poEntity.getTranTotl() == null){
//        if (poEntity.getTranTotl() <= 0.00 || poEntity.getTranTotl() == null) {
            psMessage = "Invalid Gross Amount Total.";
            return false;
        }
        
        if (poEntity.getTranTotl().equals(new BigDecimal("0.00"))){
//        if (poEntity.getTranTotl() == 0.00) {
            if (poEntity.getPayMode().equals("0")){
                psMessage = "Please Enter Amount to be transact.";
                return false;
            } 
        }
        
        if (!poEntity.getPayMode().equals("0")){
            if (poEntity.getBnkAppCD() == null ){
                psMessage = "Please select Bank to be finance.";
                return false;
            } else {
                if (poEntity.getBnkAppCD().trim().isEmpty()){
                    psMessage = "Please select Bank to be finance.";
                    return false;
                }
            }
        }
        
        if (poEntity.getNetTTotl().compareTo(new BigDecimal("0.00")) < 0 || poEntity.getNetTTotl() == null){
//        if (poEntity.getNetTTotl() < 0.00 || poEntity.getNetTTotl() == null){
            psMessage = "Invalid Net Amount Due.";
            return false;
        }
        
        if (poEntity.getOthrChrg().compareTo(new BigDecimal("0.00")) > 0){
//        if (poEntity.getOthrChrg() > 0.00){
            if (poEntity.getOthrDesc() == null ){
                psMessage = "Please enter Miscellaneous Charge Description.";
                return false;
            } else {
                if (poEntity.getOthrDesc().trim().isEmpty()){
                psMessage = "Please enter Miscellaneous Charge Description.";
                    return false;
                }
            }
        }
        
        //Validate Fleet Discount
        //if(poEntity.getIsVIP() != null){
        //    if (!poEntity.getIsVIP().equals("0")){
                if (poEntity.getFleetDsc().compareTo(new BigDecimal("0.00")) > 0){
                    if ((poEntity.getDue2Sup() == 0.00) && (poEntity.getDue2Dlr() == 0.00)){
                        psMessage = "Please set amount of rate for STD Fleet Plant / Dealer.";
                        return false;
                    }
                }

                if (poEntity.getSPFltDsc().compareTo(new BigDecimal("0.00")) > 0){
                    if ((poEntity.getSPFD2Sup() == 0.00) && (poEntity.getSPFD2Dlr() == 0.00)){
                        psMessage = "Please set amount of rate for SPL Fleet Plant / Dealer.";
                        return false;
                    }
                }
        //    }
        //}
        
        if (poEntity.getPromoDsc().compareTo(new BigDecimal("0.00")) > 0){
            if ((poEntity.getPrmD2Dlr() == 0.00) && (poEntity.getPrmD2Sup() == 0.00)){
                psMessage = "Please set amount of rate for Promo Plant / Dealer.";
                return false;
            }
        }
        
        
        //Validate TPL Insurance
        if(poEntity.getTPLStat() == null) {
            psMessage = "TPL Insurance type is not set.";
            return false;
        } else {
            if (poEntity.getTPLStat().trim().isEmpty()){
                psMessage = "TPL Insurance type  is not set.";
                return false;
            } else {
        
                switch(poEntity.getTPLStat()){
                    case "1": //FOC
                        if ((poEntity.getTPLAmt().compareTo(new BigDecimal(0.00)) > 0)){
                            psMessage = "Invalid TPL Insurance Type.";
                            return false;
                        }
                    case "3": //C/O DEALER
                        if(poEntity.getInsTplCd() == null) {
                            psMessage = "TPL Insurance is not set.";
                            return false;
                        } else {
                            if (poEntity.getInsTplCd().trim().isEmpty()){
                                psMessage = "TPL Insurance is not set.";
                                return false;
                            }
                        }

                        if(poEntity.getTPLStat().equals("3")){
                            if ((poEntity.getTPLAmt().compareTo(new BigDecimal(0.00)) <= 0)){
                                psMessage = "Invalid TPL Insurance Type.";
                                return false;
                            }
                        }

                        break;
                    case "0": //NONE
                    case "2": //C/O CLIENT
                    case "4": //C/O BANK
                        if(poEntity.getInsTplCd() != null) {
                            if (!poEntity.getInsTplCd().trim().isEmpty()){
                                psMessage = "Invalid TPL Insurance Type.";
                                return false;
                            }
                        }
                        break;
                }
            }
        }
        
        //Validate Comprehensive Insurance
        if(poEntity.getCompStat() == null) {
            psMessage = "Comprehensive Insurance type is not set.";
            return false;
        } else {
            if (poEntity.getCompStat().trim().isEmpty()){
                psMessage = "Comprehensive Insurance type  is not set.";
                return false;
            } else {
                switch(poEntity.getCompStat()){
                    case "1": //FOC
                        if (!poEntity.getInsurTyp().equals("1")){ //FREE
                            psMessage = "Invalid Comprehensive Insurance type.";
                            return false;
                        }
                        
                    case "3": //C/O DEALER
                        if(poEntity.getInsCode()== null) {
                            psMessage = "Comprehensive Insurance is not set.";
                            return false;
                        } else {
                            if (poEntity.getInsCode().trim().isEmpty()){
                                psMessage = "Comprehensive Insurance is not set.";
                                return false;
                            }
                        }
                        
                        if(poEntity.getInsurTyp() == null) {
                            psMessage = "Comprehensive Insurance type is not set.";
                            return false;
                        } else {
                            if (poEntity.getInsurTyp().trim().isEmpty()){
                                psMessage = "Comprehensive Insurance type  is not set.";
                                return false;
                            }
                        }
                        
                        if(poEntity.getInsurYr() == null) {
                            psMessage = "Comprehensive Insurance year is not set.";
                            return false;
                        } else {
                            if (poEntity.getInsurYr() == 0){
                                psMessage = "Comprehensive Insurance year is not set.";
                                return false;
                            }
                        }
                        
                        if (!poEntity.getCompStat().equals("1")){ //FREE
                            if ((poEntity.getInsurAmt().compareTo(new BigDecimal(0.00)) > 0)){
                                psMessage = "Invalid Comprehensive Insurance Amount.";
                                return false;
                            }
                        } else {
                            if ((poEntity.getInsurAmt().compareTo(new BigDecimal(0.00)) < 0)){
                                psMessage = "Invalid Comprehensive Insurance Amount.";
                                return false;
                            }
                        }
                        
                        break;
                    case "0": //NONE
                    case "2": //C/O CLIENT
                    case "4": //C/O BANK
                        if(!poEntity.getInsurTyp().equals("0")) {
                            psMessage = "Invalid Comprehensive Insurance type.";
                            return false;
                        } 
                        
                        if (poEntity.getInsurYr() > 0){
                            psMessage = "Invalid Comprehensive Insurance year.";
                            return false;
                        }
                        
                        if ((poEntity.getInsurAmt().compareTo(new BigDecimal(0.00)) > 0)){
                            psMessage = "Invalid Comprehensive Insurance Amount.";
                            return false;
                        }
                        break;
                }
            }
        }
        
        if(poEntity.getLTOStat() == null) {
            psMessage = "LTO type is not set.";
            return false;
        } else {
            if (poEntity.getLTOStat().trim().isEmpty()){
                psMessage = "LTO type is not set.";
                return false;
            } else {
                switch(poEntity.getLTOStat()){
                    case "0": //NONE
                    case "1": //FOC
                        if ((poEntity.getLTOAmt().compareTo(new BigDecimal(0.00)) > 0)){
                            psMessage = "Invalid LTO Amount.";
                            return false;
                        }
                        break;
                    case "2": //CHARGE
                        if ((poEntity.getLTOAmt().compareTo(new BigDecimal(0.00)) <= 0)){
                            psMessage = "Invalid LTO Amount.";
                            return false;
                        }
                }
            }
        }
        
        if(poEntity.getChmoStat() == null) {
            psMessage = "CHMO type is not set.";
            return false;
        } else {
            if (poEntity.getChmoStat().trim().isEmpty()){
                psMessage = "CHMO type is not set.";
                return false;
            } else {
                switch(poEntity.getChmoStat()){
                    case "0": //NONE
                    case "1": //FOC
                    case "3": //CARE OF BANK
                        if ((poEntity.getChmoAmt().compareTo(new BigDecimal(0.00)) > 0)){
                            psMessage = "Invalid CHMO Amount.";
                            return false;
                        }
                        break;
                    case "2": //CHARGE
                        if ((poEntity.getChmoAmt().compareTo(new BigDecimal(0.00)) <= 0)){
                            psMessage = "Invalid CHMO Amount.";
                            return false;
                        }
                    break;
                }
            }
        }
        
        //DO NOT allow care of bank when payment mode is CASH
        if(poEntity.getPayMode().equals("0")){
            if(poEntity.getChmoStat().equals("3")){
                psMessage = "Invalid CHMO status.";
                return false;
            }
            
            if(poEntity.getTPLStat().equals("4")){
                psMessage = "Invalid insurance TPL status.";
                return false;
            }
            
            if(poEntity.getCompStat().equals("4")){
                psMessage = "Invalid insurance comprehensive status.";
                return false;
            }
        }
        
        try {
            String lsID = "";
            String lsType = "";
            String lsDesc = "";
            String lsSQL = "";
            ResultSet loRS;
            
            //Check Existing VSP of Inquiry
            lsSQL =  " SELECT "          
                    + "   sTransNox "     
                    + " , dTransact "     
                    + " , sVSPNOxxx "     
                    + " , dDelvryDt "     
                    + " , sInqryIDx "     
                    + " , sClientID "     
                    + " , sCoCltIDx "     
                    + " , sSerialID "     
                    + " FROM vsp_master " ;

            lsSQL = MiscUtil.addCondition(lsSQL, " cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED) 
                                                    + " AND sInqryIDx = " + SQLUtil.toSQL(poEntity.getInqryID()) 
                                                    + " AND sTransNox <> " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                    );
            System.out.println("EXISTING VSP OF INQUIRY CHECK: " + lsSQL);
            loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                while(loRS.next()){
                    lsID = loRS.getString("sVSPNOxxx");
                    lsDesc = xsDateShort(loRS.getDate("dTransact"));
                }

                MiscUtil.close(loRS);

                psMessage = "Found an existing vsp record with the same client inquiry."
                            + "\n\n<VSP No:" + lsID + ">"
                            + "\n<VSP Date:" + lsDesc + ">"
                            + "\n\nSaving aborted.";
                return false;
            } 
            
            lsSQL =   " SELECT "               
                    + "   sTransNox "          
                    + " , sInqryIDx "          
                    + " , sBranchCd "          
                    + " , dTransact "          
                    + " , sSerialID "          
                    + " , cTranStat "          
                    + " , cPayModex "          
                    + " , cCustGrpx "          
                    + " FROM customer_inquiry ";
            lsSQL = MiscUtil.addCondition(lsSQL, " sTransNox = " + SQLUtil.toSQL(poEntity.getInqTran()) 
                                                    + " AND cPayModex <> " + SQLUtil.toSQL(poEntity.getPayMode())
                                                    );
            System.out.println("EXISTING INQUIRY PAYMENT MODE VS VSP PAYMENT MODE CHECK: " + lsSQL);
            loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                psMessage = "Payment Mode must be the same with inquiry.";
                MiscUtil.close(loRS);
                return false;
            }
            
            //VSP CANCELLATION
            if(poEntity.getTranStat().equals(TransactionStatus.STATE_CANCELLED)){
                //check if linked with UDR
                if (poEntity.getUDRNo() != null){
                    if (!poEntity.getUDRNo().trim().isEmpty()){
                        psMessage = "Found an existing vehicle delivery record."
                                    + "\n\n<UDR No:" + poEntity.getUDRNo() + ">"
                                    + "\n\nCancellation aborted.";
                        return false;
                    }
                }
                
                lsID = "";
                lsDesc = "";
                lsSQL =   " SELECT "         
                        + "   sTransNox "    
                        + " , dTransact "    
                        + " , cCustType "    
                        + " , sClientID "    
                        + " , sSerialID "    
                        + " , sReferNox "    
                        + " , sSourceCd "    
                        + " , sSourceNo "    
                        + " , cTranStat "    
                        + " FROM udr_master ";
                lsSQL = MiscUtil.addCondition(lsSQL, " cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED) 
                                                    + " AND sSourceNo = " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                    );
                System.out.println("EXISTING UDR CHECK: " + lsSQL);
                loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sReferNox");
                        lsDesc = xsDateShort(loRS.getDate("dTransact"));
                    }

                    MiscUtil.close(loRS);

                    psMessage = "Found an existing vehicle delivery record."
                                + "\n\n<UDR No:" + lsID + ">"
                                + "\n<UDR Date:" + lsDesc + ">"
                                + "\n\nCancellation aborted.";
                    return false;
                } 
                
                //TODO
                //check if linked with RF
                
                //check if linked with GATEPASS
                lsID = "";
                lsDesc = "";
                if (poEntity.getGatePsNo()!= null){
                    if (!poEntity.getGatePsNo().trim().isEmpty()){
                        psMessage = "Found an existing gatepass."
                                    + "\n\n<Gatepass No:" + poEntity.getGatePsNo() + ">"
                                    + "\n\nCancellation aborted.";
                        return false;
                    }
                }
                
                lsSQL =     " SELECT "                
                        + "   sTransNox "           
                        + " , dTransact "           
                        + " , sSourceGr "           
                        + " , sSourceCD "           
                        + " , sSourceNo "           
                        + " , cTranStat "           
                        + " FROM vehicle_gatepass " ;
                lsSQL = MiscUtil.addCondition(lsSQL, " cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED) 
                                                    + " AND sSourceNo = " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                    );
                System.out.println("EXISTING GATEPASS CHECK: " + lsSQL);
                loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sTransNox");
                        lsDesc = xsDateShort(loRS.getDate("dTransact"));
                    }

                    MiscUtil.close(loRS);

                    psMessage = "Found an existing gatepass."
                                + "\n\n<Gatepass No:" + lsID + ">"
                                + "\n<Gatepass Date:" + lsDesc + ">"
                                + "\n\nCancellation aborted.";
                    return false;
                } 

                //check if linked with JO
                if (poEntity.getJONo()!= null){
                    if (!poEntity.getJONo().trim().isEmpty()){
                        psMessage = "Found an existing job order record."
                                    + "\n\n<JO No:" + poEntity.getJONo() + ">"
                                    + "\n\nCancellation aborted.";
                        return false;
                    }
                }
                
                //Existing JO
                lsID = "";
                lsDesc = "";
                lsSQL =   " SELECT "                
                        + "   sTransNox "           
                        + " , dTransact "           
                        + " , sDSNoxxxx "           
                        + " , sSerialID "           
                        + " , sClientID "           
                        + " , sSourceNo "           
                        + " , sSourceCD "           
                        + " , cTranStat "           
                        + " FROM diagnostic_master ";
                lsSQL = MiscUtil.addCondition(lsSQL, " cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED) 
                                                    + " AND sSourceNo = " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                    );
                System.out.println("EXISTING JO CHECK: " + lsSQL);
                loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sDSNoxxxx");
                        lsDesc = xsDateShort(loRS.getDate("dTransact"));
                    }

                    MiscUtil.close(loRS);

                    psMessage = "Found an existing job order record."
                                + "\n\n<JO No:" + lsID + ">"
                                + "\n<JO Date:" + lsDesc + ">"
                                + "\n\nCancellation aborted.";
                    return false;
                } 

                //check if linked with ins proposal
                lsID = "";
                lsDesc = "";
                lsSQL =   " SELECT "                        
                        + "   sTransNox "                   
                        + " , dTransact "                   
                        + " , sReferNox "                   
                        + " , sClientID "                   
                        + " , sSerialID "                   
                        + " , sVSPNoxxx "                   
                        + " , cTranStat "                   
                        + " FROM insurance_policy_proposal ";
                lsSQL = MiscUtil.addCondition(lsSQL, " cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED) 
                                                    + " AND sVSPNoxxx = " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                    );
                System.out.println("EXISTING INS PROPOSAL CHECK: " + lsSQL);
                loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sReferNox");
                        lsDesc = xsDateShort(loRS.getDate("dTransact"));
                    }

                    MiscUtil.close(loRS);

                    psMessage = "Found an existing insurance proposal record."
                                + "\n\n<Proposal No:" + lsID + ">"
                                + "\n<Proposal Date:" + lsDesc + ">"
                                + "\n\nCancellation aborted.";
                    return false;
                }

                //TODO
                //check if linked with chmo/lto registration

                //no need to check with VSI as VSI linking is thru udr_code and udr code has been validated already, see up

                //PAYMENT
                //check if linked with CC
                //check if linked with PR
                lsID = "";
                lsDesc = "";
                lsType = "";
                lsSQL =   " SELECT "                                             
                        + "   a.sTransNox "                                     
                        + " , a.sReferNox "                                     
                        + " , a.sSourceCD "                                     
                        + " , a.sSourceNo "                                     
                        + " , a.sTranType "                                     
                        + " , b.sReferNox AS sSINoxxxx "                        
                        + " , b.dTransact "                      
                        + " , b.cTranStat  "                                  
                        + " FROM si_master_source a "                           
                        + " LEFT JOIN si_master b ON b.sTransNox = a.sTransNox ";
                lsSQL = MiscUtil.addCondition(lsSQL, " b.cTranStat <> " + SQLUtil.toSQL(TransactionStatus.STATE_CANCELLED) 
                                                    + " AND a.sReferNox = " + SQLUtil.toSQL(poEntity.getTransNo()) 
                                                    );
                System.out.println("EXISTING PAYMENT CHECK: " + lsSQL);
                loRS = poGRider.executeQuery(lsSQL);

                if (MiscUtil.RecordCount(loRS) > 0){
                    while(loRS.next()){
                        lsID = loRS.getString("sSINoxxxx");
                        lsType = loRS.getString("sTranType"); //TODO
                        lsDesc = xsDateShort(loRS.getDate("dTransact"));
                    }

                    MiscUtil.close(loRS);

                    psMessage = "Found an existing payment."
                                + "\n\n<Invoice No:" + lsID + ">"
                                + "\n<Invoice Date:" + lsDesc + ">"
                                + "\n<Invoice Type:" + lsType + ">"
                                + "\n\nCancellation aborted.";
                    return false;
                }


                //TODO ACCOUNTING RELATED TABLE
                //check if linked with SOA
                //check if linked with AP
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(Validator_VehicleSalesProposal_Master.class.getName()).log(Level.SEVERE, null, ex);
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
