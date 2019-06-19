package com.agent.czb.core.cooper.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/1/7.
 */
public class CooperReturn {
    private String id;
    private String gate;
    private String carcode;
    private String username;
    private Date starttime;
    private Date endtime;
    private Date time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarcode() {
        return carcode;
    }

    public void setCarcode(String carcode) {
        this.carcode = carcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    @Override
    public String toString() {
        return "CooperReturn{" +
                "id='" + id + '\'' +
                ", carcode='" + carcode + '\'' +
                ", username='" + username + '\'' +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", time=" + time +
                '}';
    }
}
