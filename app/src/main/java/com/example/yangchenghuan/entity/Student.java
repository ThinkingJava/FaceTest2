package com.example.yangchenghuan.entity;

import java.io.Serializable;

/**
 * Created by 杨城欢 on 2016/9/11.
 */
public class Student  implements Serializable {
    private int id;
    private String name;
    private String faceid;
    private String grade;
    private String imagepath;

    public Student(){}
    public Student(int id, String name,String faceid ,String grade,String imagepath) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.imagepath = imagepath;
    }

    public Student(String name, String faceid,String grade,String imagepath) {

        this.name = name;
        this.faceid = faceid;
        this.grade = grade;
        this.imagepath=imagepath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFaceid() {
        return faceid;
    }

    public void setFaceid(String faceid) {
        this.faceid = faceid;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
