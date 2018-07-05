package com.example.admin.itrack.models;

public class Announcement {
    private int announcementId;
    private String sender;
    private String announcemntTitle;
    private String annoucementDescription;
    private String timestamp;
    private String picture;
    private boolean isImportant;
    private boolean isRead;
    private int color = -1;


    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAnnouncemntTitle() {
        return announcemntTitle;
    }

    public void setAnnouncemntTitle(String announcemntTitle) {
        this.announcemntTitle = announcemntTitle;
    }

    public String getAnnoucementDescription() {
        return annoucementDescription;
    }

    public void setAnnoucementDescription(String annoucementDescription) {
        this.annoucementDescription = annoucementDescription;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }




}
