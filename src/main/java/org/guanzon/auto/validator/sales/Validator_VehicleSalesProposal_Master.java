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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
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
        
        if(poEntity.getPayMode() == null) {
            psMessage = "Payment mode is not set.";
            return false;
        } else {
            if (poEntity.getPayMode().trim().isEmpty()){
                psMessage = "Payment mode is not set.";
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
        
        if (poEntity.getTranTotl() <= 0.00 || poEntity.getTranTotl() == null) {
            psMessage = "Invalid Gross Amount Total.";
            return false;
        }
        
        if (poEntity.getTranTotl() == 0.00) {
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
        
        if (poEntity.getNetTTotl() < 0.00 || poEntity.getNetTTotl() == null){
            psMessage = "Invalid Net Amount Due.";
            return false;
        }
        
        if (poEntity.getOthrChrg() > 0.00){
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
        if(poEntity.getIsVIP() != null){
            if (!poEntity.getIsVIP().equals("0")){
                if (poEntity.getFleetDsc()> 0.00){
                    if ((poEntity.getDue2Sup() == 0.00) && (poEntity.getDue2Dlr() == 0.00)){
                        psMessage = "Please set amount of rate for Plant / Dealer.";
                        return false;
                    }
                }

                if (poEntity.getSPFltDsc() > 0.00){
                    if ((poEntity.getSPFD2Sup() == 0.00) && (poEntity.getSPFD2Dlr() == 0.00)){
                        psMessage = "Please set amount of rate for Plant / Dealer.";
                        return false;
                    }
                }
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

            lsSQL = MiscUtil.addCondition(lsSQL, " cTranStat <> '0' "
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
            lsSQL = MiscUtil.addCondition(lsSQL, " sTransNox = " + SQLUtil.toSQL(poEntity.getInqryID()) 
                                                    + " cPayModex <> " + SQLUtil.toSQL(poEntity.getPayMode())
                                                    );
            System.out.println("EXISTING INQUIRY PAYMENT MODE VS VSP PAYMENT MODE CHECK: " + lsSQL);
            loRS = poGRider.executeQuery(lsSQL);

            if (MiscUtil.RecordCount(loRS) > 0){
                psMessage = "Payment Mode must be the same with inquiry.";
                MiscUtil.close(loRS);
                return false;
            }
            
            //Validate Insurance
            //TPL
            switch (poEntity.getTPLStat()){
                case "1":
                    if (poEntity.getInsTplCd() == null){
                        psMessage = "Please select Insurance Company.";
                        return false;
                    } else { 
                        if (poEntity.getInsTplCd().trim().isEmpty()){
                            psMessage = "Please select Insurance Company.";
                            return false;
                        }
                    }

                    if (poEntity.getTPLAmt() > 0.00){
                        psMessage = "Amount cannot be more than 0.00 if TPL status is FOC.";
                        return false;
                    }
                break;
                case "3":
                    if (poEntity.getInsTplCd() == null){
                        psMessage = "Please select Insurance Company.";
                        return false;
                    } else {
                        if (poEntity.getInsTplCd().isEmpty()){
                            psMessage = "Please select Insurance Company.";
                            return false;
                        }
                    }

                    if (poEntity.getTPLAmt() <= 0.00){
                        psMessage = "Amount cannot be 0.00 if TPL status is not FOC.";
                        return false;
                    }
                break;

            }

            //COMPRE
            switch(poEntity.getCompStat()){
                case "1":
                case "3":
                    if (poEntity.getInsCode() == null){
                        psMessage = "Please select Insurance Company.";
                        return false;
                    } else {
                        if (poEntity.getInsCode().isEmpty()){
                            psMessage = "Please select Insurance Company.";
                            return false;
                        }
                    }

                    if (poEntity.getCompStat().equals("1")){
                        if (poEntity.getCompAmt() > 0.00){
                            psMessage = "Amount cannot be more than 0.00 if COMPRE status is FOC.";
                            return false;
                        }
                    } else {
                        if (poEntity.getCompAmt() <= 0.00){
                            psMessage = "Amount cannot be 0.00 if COMPRE status is not FOC.";
                            return false;
                        }
                    }

                    if (poEntity.getCompStat().equals("3")){
                        if (poEntity.getInsurTyp().equals("0")){
                            psMessage = "Please select Insurance Type.";
                            return false;

                        }
                        if (poEntity.getInsurYr() == null){
                            psMessage = "Please select Insurance Year.";
                            return false;
                        } else {
                            if (poEntity.getInsurYr() == 0){
                                psMessage = "Please select Insurance Year.";
                                return false;

                            }
                        }
                    } 

                    break;
            }
            
            //VSP CANCELLATION
            if(poEntity.getTranStat().equals("0")){
                //check if linked with UDR
                if (poEntity.getUDRNo() != null){
                    if (!poEntity.getUDRNo().trim().isEmpty()){
                        psMessage = "Found an existing vehicle delivery record."
                                    + "\n\n<UDR No:" + lsID + ">"
                                    + "\n\nCancellation aborted.";
                        return false;
                    }
                }
                
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
                lsSQL = MiscUtil.addCondition(lsSQL, " cTranStat <> '0' "
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
                if (poEntity.getGatePsNo()!= null){
                    if (!poEntity.getGatePsNo().trim().isEmpty()){
                        psMessage = "Found an existing job order record."
                                    + "\n\n<Gatepass No:" + lsID + ">"
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
                lsSQL = MiscUtil.addCondition(lsSQL, " cTranStat <> '0' "
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

                    psMessage = "Found an existing gatepass record."
                                + "\n\n<Gatepass No:" + lsID + ">"
                                + "\n<Gatepass Date:" + lsDesc + ">"
                                + "\n\nCancellation aborted.";
                    return false;
                } 

                //check if linked with JO
                if (poEntity.getJONo()!= null){
                    if (!poEntity.getJONo().trim().isEmpty()){
                        psMessage = "Found an existing job order record."
                                    + "\n\n<JO No:" + lsID + ">"
                                    + "\n\nCancellation aborted.";
                        return false;
                    }
                }
                
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
                lsSQL = MiscUtil.addCondition(lsSQL, " cTranStat <> '0' "
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
                lsSQL =   " SELECT "                        
                        + "   sTransNox "                   
                        + " , dTransact "                   
                        + " , sReferNox "                   
                        + " , sClientID "                   
                        + " , sSerialID "                   
                        + " , sVSPNoxxx "                   
                        + " , cTranStat "                   
                        + " FROM insurance_policy_proposal ";
                lsSQL = MiscUtil.addCondition(lsSQL, " cTranStat <> '0' "
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
                lsSQL = MiscUtil.addCondition(lsSQL, " b.cTranStat <> '0' "
                                                    + " AND a.sSourceNo = " + SQLUtil.toSQL(poEntity.getTransNo()) 
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
