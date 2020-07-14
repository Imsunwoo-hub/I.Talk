package com.example.user.new_italk.JAVA_Bean;

public class NoticeComment {
    String type, commentNum, noticeNum, comment, commentWriterId, commentWriterName, writtenDateTime, repliedCommentNum;

    public NoticeComment(String type, String commentNum, String noticeNum, String comment, String commentWriterId, String commentWriterName, String writtenDateTime, String repliedCommentNum) {
        this.type = type;
        this. commentNum = commentNum;
        this. noticeNum = noticeNum;
        this.comment = comment;
        this.commentWriterId = commentWriterId;
        this.commentWriterName = commentWriterName;
        this.writtenDateTime = writtenDateTime;
        this.repliedCommentNum = repliedCommentNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getNoticeNum() {
        return noticeNum;
    }

    public void setNoticeNum(String noticeNum) {
        this.noticeNum = noticeNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentWriterId() {
        return commentWriterId;
    }

    public void setCommentWriterId(String commentWriterId) {
        this.commentWriterId = commentWriterId;
    }

    public String getCommentWriterName() {
        return commentWriterName;
    }

    public void setCommentWriterName(String commentWriterName) {
        this.commentWriterName = commentWriterName;
    }

    public String getWrittenDateTime() {
        return writtenDateTime;
    }

    public void setWrittenDateTime(String writtenDateTime) {
        this.writtenDateTime = writtenDateTime;
    }

    public String getRepliedCommentNum() {
        return repliedCommentNum;
    }

    public void setRepliedCommentNum(String repliedCommentNum) {
        this.repliedCommentNum = repliedCommentNum;
    }
}
