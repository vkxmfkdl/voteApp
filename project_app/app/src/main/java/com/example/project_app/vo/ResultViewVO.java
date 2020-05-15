package com.example.project_app.vo;

public class ResultViewVO {

    private String huboid;
    private String name; // 이름
    private String hanjaName;
    private String jdName; // 정당
    private String sdName; //시
    private String birthday;
    private String sggName;

    public ResultViewVO() { }

    public ResultViewVO(String huboid, String name, String hanjaName, String jdName, String sdName, String birthday, String sggName) {
        this.huboid = huboid;
        this.name = name;
        this.hanjaName = hanjaName;
        this.jdName = jdName;
        this.sdName = sdName;
        this.birthday = birthday;
        this.sggName = sggName;
    }

    public String getHuboid() {
        return huboid;
    }

    public String getName() {
        return name;
    }

    public String getHanjaName() {
        return hanjaName;
    }

    public String getJdName() {
        return jdName;
    }

    public String getSdName() {
        return sdName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSggName() {
        return sggName;
    }

    public void setHuboid(String huboid) {
        this.huboid = huboid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHanjaName(String hanjaName) {
        this.hanjaName = hanjaName;
    }

    public void setJdName(String jdName) {
        this.jdName = jdName;
    }

    public void setSdName(String sdName) {
        this.sdName = sdName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSggName(String sggName) {
        this.sggName = sggName;
    }

    @Override
    public String toString() {
        return "ResultViewVO{" +
                "huboid='" + huboid + '\'' +
                ", name='" + name + '\'' +
                ", hanjaName='" + hanjaName + '\'' +
                ", jdName='" + jdName + '\'' +
                ", sdName='" + sdName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sggName='" + sggName + '\'' +
                '}';
    }
}
