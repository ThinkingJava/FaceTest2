package com.example.yangchenghuan.entity;

import java.io.Serializable;

/**
 * Created by 杨城欢 on 2016/9/16.
 */
public class Grade implements Serializable {
    private int id;
    private String name;
    private String crowdid;

    public Grade() {
    }

    public Grade(int id, String name, String crowdid) {
        this.id = id;
        this.name = name;
        this.crowdid = crowdid;
    }

    public Grade(String name, String crowdid) {
        this.name = name;
        this.crowdid = crowdid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrowdid() {
        return crowdid;
    }

    public void setCrowdid(String crowdid) {
        this.crowdid = crowdid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
