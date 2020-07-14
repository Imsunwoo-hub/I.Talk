package com.example.user.new_italk.JAVA_Bean;

import java.util.Date;

public class Inquiry {

    int inquiryNum;
    String user1;
    String user2;
    String lastComment;
    Date lastCommentDateTime;
    public boolean checked;

    public Inquiry(int inquiryNum, String user1, String user2, String lastComment, Date lastCommentDateTime, boolean checked) {
        this.inquiryNum = inquiryNum;
        this.user1 = user1;
        this.user2 = user2;
        this.lastComment = lastComment;
        this.lastCommentDateTime = lastCommentDateTime;
        this.checked = checked;
    }

    public int getInquiryNum() {
        return inquiryNum;
    }

    public void setInquiryNum(int inquiryNum) {
        this.inquiryNum = inquiryNum;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getLastComment() {
        return lastComment;
    }

    public void setLastComment(String lastComment) {
        this.lastComment = lastComment;
    }

    public Date getLastCommentDateTime() {
        return lastCommentDateTime;
    }

    public void setLastCommentDateTime(Date lastCommentDateTime) {
        this.lastCommentDateTime = lastCommentDateTime;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
