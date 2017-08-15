package com.home.we.yuandiary.bean;

import java.util.List;

/**
 * Created by pactera on 2017/8/15.
 */

public class QA {
    private String qa_qname;
    private String qa_lable;
    private String qa_person_head;
    private String qa_person;
    private String qa_time;
    private String qa_count;
    private String qa_picture;


    public String getQa_qname() {
        return qa_qname;
    }

    public void setQa_qname(String qa_qname) {
        this.qa_qname = qa_qname;
    }

    public String getQa_lable() {
        return qa_lable;
    }

    public void setQa_lable(String qa_lable) {
        this.qa_lable = qa_lable;
    }

    public String getQa_person_head() {
        return qa_person_head;
    }

    public void setQa_person_head(String qa_person_head) {
        this.qa_person_head = qa_person_head;
    }

    public String getQa_person() {
        return qa_person;
    }

    public void setQa_person(String qa_person) {
        this.qa_person = qa_person;
    }

    public String getQa_time() {
        return qa_time;
    }

    public void setQa_time(String qa_time) {
        this.qa_time = qa_time;
    }

    public String getQa_count() {
        return qa_count;
    }

    public void setQa_count(String qa_count) {
        this.qa_count = qa_count;
    }

    public String getQa_picture() {
        return qa_picture;
    }

    public void setQa_picture(String qa_picture) {
        this.qa_picture = qa_picture;
    }

    public QA(String qa_qname, String qa_lable, String qa_person_head, String qa_person, String qa_time, String qa_count, String qa_picture) {
        this.qa_qname = qa_qname;
        this.qa_lable = qa_lable;
        this.qa_person_head = qa_person_head;
        this.qa_person = qa_person;
        this.qa_time = qa_time;
        this.qa_count = qa_count;
        this.qa_picture = qa_picture;
    }

    @Override
    public String toString() {
        return "QA{" +
                "qa_qname='" + qa_qname + '\'' +
                ", qa_lable='" + qa_lable + '\'' +
                ", qa_person_head='" + qa_person_head + '\'' +
                ", qa_person='" + qa_person + '\'' +
                ", qa_time='" + qa_time + '\'' +
                ", qa_count='" + qa_count + '\'' +
                ", qa_picture='" + qa_picture + '\'' +
                '}';
    }
}
