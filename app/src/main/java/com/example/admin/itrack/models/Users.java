package com.example.admin.itrack.models;

public class Users {
    private int userId;
    private String username;
    private String password;
    private String fullname;
    private String image;
    private String usertype;
    private String minorFullName;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public static Users userInstance;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Users getInstance(){
        if(userInstance == null) {
            return userInstance = new Users();
        }
        return userInstance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }


    public String getMinorFullName() {
        return minorFullName;
    }

    public void setMinorFullName(String minorFullName) {
        this.minorFullName = minorFullName;
    }
}
