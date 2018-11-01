package com.citi.isg.omc.issueDatabase;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String issueDescription;
    private String solution;
    private String jira;
    private String workaround;
    private String attendee;
    private String lastMod;

    public Issue() {
    }

    public String getWorkaround() {
        return workaround;
    }

    public void setWorkaround(String workaround) {
        this.workaround = workaround;
    }

    public String getLastMod() {
        return lastMod;
    }

    public void setLastMod(String setLastMod) {
        this.lastMod = setLastMod;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issue) {
        this.issueDescription = issue;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getJira() {
        return jira;
    }

    public void setJira(String jira) {
        this.jira = jira;
    }


    public String getAttendee() {
        return attendee;
    }

    public void setAttendee(String solvedBy) {
        this.attendee = solvedBy;
    }

    @Override
    public String toString() {
        return "IssueDatabase{" +
                "id=" + id +
                ", issueDescription='" + issueDescription + '\'' +
                ", solution='" + solution + '\'' +
                ", jira='" + jira + '\'' +
                ", workaorund='" + workaround + '\'' +
                ", attendee='" + attendee + '\'' +
                '}';
    }


}
