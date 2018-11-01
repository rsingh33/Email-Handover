package com.citi.isg.omc.handover;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HandoverRow {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String lastMod;
    private String reportedBy;
    private String email;
    private String tracking;
    private String comments;
    private String status;
    private String currentlyWith;

    public String getCurrentlyWith() {
        return currentlyWith;
    }

    public void setCurrentlyWith(String currentlyWith) {
        this.currentlyWith = currentlyWith;
    }



    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    private String environment;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getLastMod() {
        return lastMod;
    }

    public void setLastMod(String lastMod) {
        this.lastMod = lastMod;
    }


    public HandoverRow(long id, String reportedBy, String email, String tracking, String comments , String status) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public HandoverRow() {

    }

    public HandoverRow(String reportedBy, String email, String tracking, String comments ,String status,String environment) {
        this.email = email;
        this.tracking = tracking;
        this.comments = comments;
        this.reportedBy = reportedBy;
        this.status = status;
        this.environment = environment;
    }


}
