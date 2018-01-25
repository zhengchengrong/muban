package com.threehmis.bjaj.api.bean;

/**
 * Created by llz on 2018/1/24.
 */

public class TaskEntity {

    private String projectName;
    private String projectCheck;
    private String projectNum;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCheck() {
        return projectCheck;
    }

    public void setProjectCheck(String projectCheck) {
        this.projectCheck = projectCheck;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }
}
