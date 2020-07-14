package com.example.user.new_italk.JAVA_Bean;

public class Notice {
    String num, classification, title, content, writer, date, topFixed;
    public Notice(String num, String classification, String title, String content, String writer, String date, String topFixed) {
        this.num = num;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.date = date;
        this.topFixed = topFixed;
        this.classification = classification;
    }
    public String getNum() { return num; }

    public void setNum(String num) { this.num = num; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopFixed() {
        return topFixed;
    }

    public void setTopFixed(String topFixed) {
        this.topFixed = topFixed;
    }

    public String getClassification() {
        return classification;
    }
    public void setClassification(String classification) {
        this.classification = classification;
    }
}
