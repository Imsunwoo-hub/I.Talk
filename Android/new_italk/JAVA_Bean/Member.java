package com.example.user.new_italk.JAVA_Bean;

public class Member {

    public String memberid;
    public String membername;
    String membergrade;
    String membermajor;
    String memberidentity;
    String memberstate;
    public boolean checkMember;

    public Member(String memberid, String membername, String membergrade, String membermajor, String memberidentity, boolean checkMember, String memberstate) {
        this.memberid = memberid;
        this.membername = membername;
        this.membergrade = membergrade;
        this.membermajor = membermajor;
        this.memberidentity = memberidentity;
        this.checkMember = checkMember;
        this.memberstate = memberstate;
    }

    public String getMemberstate() {
        return memberstate;
    }

    public void setMemberstate(String memberstate) {
        this.memberstate = memberstate;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getMembergrade() {
        return membergrade;
    }

    public void setMembergrade(String membergrade) {
        this.membergrade = membergrade;
    }

    public String getMembermajor() {
        return membermajor;
    }

    public void setMembermajor(String membermajor) {
        this.membermajor = membermajor;
    }

    public String getMemberidentity() {
        return memberidentity;
    }

    public void setMemberidentity(String memberidentity) {
        this.memberidentity = memberidentity;
    }

    public Boolean getChecked() {
        return checkMember;
    }

    public void setCheckMember(boolean checkMember) {
        this.checkMember = checkMember;
    }
}
