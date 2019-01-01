package com.lanyu96.querylogistics.bean;

public class LocAndTimeInfo {
    private String time;
    private String loc;

    public LocAndTimeInfo(String time, String loc) {
        this.time = time;
        this.loc = loc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
