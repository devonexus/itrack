package com.example.admin.itrack.models;

public class EmergencyType {
    private int emergencyTypeId;
    private String amergency_date;
    private String emergencyTypeName;
    private String emergencyPic;
    public String getEmergencyPic() {
        return emergencyPic;
    }

    public void setEmergencyPic(String emergencyPic) {
        this.emergencyPic = emergencyPic;
    }


    public static EmergencyType emergencyType;
    public int getEmergencyTypeId() {
        return emergencyTypeId;
    }

    public void setEmergencyTypeId(int emergencyTypeId) {
        this.emergencyTypeId = emergencyTypeId;
    }

    public String getAmergency_date() {
        return amergency_date;
    }

    public void setAmergency_date(String amergency_date) {
        this.amergency_date = amergency_date;
    }

    public String getEmergencyTypeName() {
        return emergencyTypeName;
    }

    public void setEmergencyTypeName(String emergencyTypeName) {
        this.emergencyTypeName = emergencyTypeName;
    }

    public static EmergencyType getEmergencyType() {
        return emergencyType;
    }

    public static void setEmergencyType(EmergencyType emergencyType) {
        EmergencyType.emergencyType = emergencyType;
    }




    public static EmergencyType getInstance(){
        if(emergencyType == null){
            return emergencyType = new EmergencyType();
        }
        return emergencyType;
    }








    

}
