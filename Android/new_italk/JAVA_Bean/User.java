package com.example.user.new_italk.JAVA_Bean;

public class User {

    String userid;
    String username;
    String usergrade;
    String usermajor;
    String useridentity;
    String userphonenum;
    String useremail;


    public User(String userid, String username, String usergrade, String usermajor, String useridentity, String userphonenum, String useremail) {
        this.userid = userid;
        this.username = username;
        this.usergrade = usergrade;
        this.usermajor = usermajor;
        this.useridentity = useridentity;
        this.userphonenum = userphonenum;
        this.useremail = useremail;

    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsergrade() {
        return usergrade;
    }

    public void setUsergrade(String usergrade) {
        this.usergrade = usergrade;
    }

    public String getUsermajor() {
        return usermajor;
    }

    public void setUsermajor(String usermajor) {
        this.usermajor = usermajor;
    }

    public String getUseridentity() {
        return useridentity;
    }

    public void setUseridentity(String useridentity) {
        this.useridentity = useridentity;
    }

    public String getUserphonenum() {
        return userphonenum;
    }

    public void setUserphonenum(String userphonenum) {
        this.userphonenum = userphonenum;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

}

