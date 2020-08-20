package com.example.user.new_italk.JAVA_Bean;

public class Group {
    int groupnum;
    String groupname;
    String groupadministrator;
    public boolean checkGroup;

    public Group(int groupnum, String groupname, String groupadministrator, boolean checkGroup) {
        this.groupnum = groupnum;
        this.groupname = groupname;
        this.groupadministrator = groupadministrator;
        this.checkGroup = checkGroup;
    }

    public int getGroupnum() {
        return groupnum;
    }

    public void setGroupnum(int groupnum) {
        this.groupnum = groupnum;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupadministrator() {
        return groupadministrator;
    }

    public void setGroupadministrator(String groupadministrator) {
        this.groupadministrator = groupadministrator;
    }

    public Boolean getCheckGroup(){ return checkGroup;}

    public void setCheckGroup(boolean checkGroup){
        this.checkGroup = checkGroup;
    }

}
