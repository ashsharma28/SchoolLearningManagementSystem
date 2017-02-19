package com.example.ashishsharma.temporayappname;

public enum CurrentUser {
    INSTANCE;
    public String userName;
    public String profilePic;
    public String emailId="";
    public String provider;
    public boolean isFirstTime(){
        if(emailId.equalsIgnoreCase(""))return true;
        return false;
    }
    public boolean isLoggedIn(){

        return true;
    }


}