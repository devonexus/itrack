package com.example.admin.itrack.models;


/*
** POJO for table parent assign
**
*/

public class TableParentAssign {
    private String relationship_to_minor;
    private String notification_date;
    private String parent_id;
    private String minor_id;

    public String getMinorFullName() {
        return minorFullName;
    }

    public void setMinorFullName(String minorFullName) {
        this.minorFullName = minorFullName;
    }

    private String minorFullName;
    public String getRelationship_to_minor() {
        return relationship_to_minor;
    }

    public void setRelationship_to_minor(String relationship_to_minor) {
        this.relationship_to_minor = relationship_to_minor;
    }

    public String getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(String notification_date) {
        this.notification_date = notification_date;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getMinor_id() {
        return minor_id;
    }

    public void setMinor_id(String minor_id) {
        this.minor_id = minor_id;
    }


}
