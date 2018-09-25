package com.example.admin.itrack.models;

public class AppToken {
    private String appToken;
    public static AppToken appTokenInstance;
    public static AppToken getInstance(){
        if(appTokenInstance == null){
            return new AppToken();
        }
        return appTokenInstance;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }


}
