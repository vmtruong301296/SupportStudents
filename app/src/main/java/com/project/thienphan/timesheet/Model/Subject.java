package com.project.thienphan.timesheet.Model;

public class Subject {
    private String SubCode;
    private String SubName;

    public String getSubCode() {
        return SubCode;
    }

    public void setSubCode(String subCode) {
        SubCode = subCode;
    }

    public String getSubName() {
        return SubName;
    }

    public void setSubName(String subName) {
        SubName = subName;
    }

    public Subject() {

    }

    public Subject(String subCode, String subName) {

        SubCode = subCode;
        SubName = subName;
    }
}
