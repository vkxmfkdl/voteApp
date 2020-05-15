package com.example.project_app.vo;

import android.graphics.Bitmap;

import java.util.Arrays;

public class InfoDetailVO {

    private String name; // 이름
    private String huboid;
    private String hanjaName;
    private String birthday; // 출생월일
    private String jdName; // 정당
    private String sgTypeCode;//구분
    private String sdName; //시
    private String sggName; //광진구갑
    private String edu;
    private String job;
    private String career1;
    private String career2;

    public InfoDetailVO(String name, String huboid, String hanjaName, String birthday, String jdName, String sgTypeCode, String sdName, String sggName, String edu, String job, String career1, String career2) {
        this.name = name;
        this.huboid = huboid;
        this.hanjaName = hanjaName;
        this.birthday = birthday;
        this.jdName = jdName;
        this.sgTypeCode = sgTypeCode;
        this.sdName = sdName;
        this.sggName = sggName;
        this.edu = edu;
        this.job = job;
        this.career1 = career1;
        this.career2 = career2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHuboid() {
        return huboid;
    }

    public void setHuboid(String huboid) {
        this.huboid = huboid;
    }

    public String getHanjaName() {
        return hanjaName;
    }

    public void setHanjaName(String hanjaName) {
        this.hanjaName = hanjaName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJdName() {
        return jdName;
    }

    public void setJdName(String jdName) {
        this.jdName = jdName;
    }

    public String getSgTypeCode() {
        return sgTypeCode;
    }

    public void setSgTypeCode(String sgTypeCode) {
        this.sgTypeCode = sgTypeCode;
    }

    public String getSdName() {
        return sdName;
    }

    public void setSdName(String sdName) {
        this.sdName = sdName;
    }

    public String getSggName() {
        return sggName;
    }

    public void setSggName(String sggName) {
        this.sggName = sggName;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCareer1() {
        return career1;
    }

    public void setCareer1(String career1) {
        this.career1 = career1;
    }

    public String getCareer2() {
        return career2;
    }

    public void setCareer2(String career2) {
        this.career2 = career2;
    }

    @Override
    public String toString() {
        return "InfoDetailVO{" +
                "name='" + name + '\'' +
                ", huboid='" + huboid + '\'' +
                ", hanjaName='" + hanjaName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", jdName='" + jdName + '\'' +
                ", sgTypeCode='" + sgTypeCode + '\'' +
                ", sdName='" + sdName + '\'' +
                ", sggName='" + sggName + '\'' +
                ", edu='" + edu + '\'' +
                ", job='" + job + '\'' +
                ", career1='" + career1 + '\'' +
                ", career2='" + career2 + '\'' +
                '}';
    }
}
