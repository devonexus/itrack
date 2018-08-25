package com.example.admin.itrack.models;

public class MinorLocation {
    public static MinorLocation minorLocationInstance;
    private  String latitude;
    private String longitude;
    private String timespan;
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimespan() {
        return timespan;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }

    public static MinorLocation getInstance(){
        if(minorLocationInstance == null) {
            return minorLocationInstance = new MinorLocation();
        }
        return minorLocationInstance;
    }

}
