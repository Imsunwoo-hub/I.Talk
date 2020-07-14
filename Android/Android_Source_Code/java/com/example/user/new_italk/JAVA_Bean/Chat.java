package com.example.user.new_italk.JAVA_Bean;

import java.util.Date;

public class Chat {

    String sender;
    String comment;
    Date commentDateTime;


    public Chat(String sender, String comment, Date commentDateTime) {
        this.sender = sender;
        this.comment = comment;
        this.commentDateTime = commentDateTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentDateTime() {
        return commentDateTime;
    }

    public void setCommentDateTime(Date commentDateTime) {
        this.commentDateTime = commentDateTime;
    }


}
