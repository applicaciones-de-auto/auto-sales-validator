/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guanzon.auto.validator.sales;

/**
 *
 * @author Arsiela
 */
public class ValidatorFactory {
    
    public enum TYPE{
        Activity_Master,
        Activity_Member,
        Activity_Town,
        Activity_Vehicle,
        Inquiry_Master,
        Inquiry_Vehicle_Priority,
        Inquiry_Promo,
        Inquiry_Requiremets,
        Inquiry_Reservation,
        Inquiry_BankApplication,
        Inquiry_FollowUp,
        VehicleSalesProposal_Master,
        VehicleSalesProposal_Finance,
        VehicleSalesProposal_Labor,
        VehicleSalesProposal_Parts,
        VehicleDeliveryReceipt_Master
    }
    
    public static ValidatorInterface make(ValidatorFactory.TYPE foType, Object foValue){
        switch (foType) {
            case Activity_Master:
                return new Validator_Activity_Master(foValue);
            case Activity_Member:
                return new Validator_Activity_Member(foValue);
            case Activity_Town:
                return new Validator_Activity_Town(foValue);
            case Activity_Vehicle:
                return new Validator_Activity_Vehicle(foValue);
            case Inquiry_Master:
                return new Validator_Inquiry_Master(foValue);
            case Inquiry_Vehicle_Priority:
                return new Validator_Inquiry_Vehicle_Priority(foValue);
            case Inquiry_Promo:
                return new Validator_Inquiry_Promo(foValue);
            case Inquiry_Requiremets:
                return new Validator_Inquiry_Requiremets(foValue);
            case Inquiry_Reservation:
                return new Validator_Inquiry_Reservation(foValue);
            case Inquiry_BankApplication:
                return new Validator_Inquiry_BankApplication(foValue);
            case Inquiry_FollowUp:
                return new Validator_Inquiry_FollowUp(foValue);
            case VehicleSalesProposal_Master:
                return new Validator_VehicleSalesProposal_Master(foValue);
            case VehicleSalesProposal_Finance:
                return new Validator_VehicleSalesProposal_Finance(foValue);
            case VehicleSalesProposal_Labor:
                return new Validator_VehicleSalesProposal_Labor(foValue);
            case VehicleSalesProposal_Parts:
                return new Validator_VehicleSalesProposal_Parts(foValue);
            case VehicleDeliveryReceipt_Master:
                return new Validator_VehicleDeliveryReceipt_Master(foValue);
            default:
                return null;
        }
    }
    
}
