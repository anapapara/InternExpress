package com.example.internexpress.domain;

import java.util.ArrayList;
import java.util.List;

public class Internship extends Entity<Integer> {
    private String title;
    private String duration;
    private String domain;
    private String internshipType;
    private String startDate;
    private String description;
    private String detailsLink;
    private User createdBy;

    private List<User> applicants;

    public Internship(String title, String duration, String domain, String internshipType, String startDate, String description, String detailsLink, User createdBy) {
        this.title = title;
        this.duration = duration;
        this.domain = domain;
        this.internshipType = internshipType;
        this.startDate = startDate;
        this.description = description;
        this.detailsLink = detailsLink;
        this.createdBy = createdBy;
        this.applicants = new ArrayList<>();
    }

    public void addApplicant(User applicant) {
        this.applicants.add(applicant);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getInternshipType() {
        return internshipType;
    }

    public void setInternshipType(String internshipType) {
        this.internshipType = internshipType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailsLink() {
        return detailsLink;
    }

    public void setDetailsLink(String detailsLink) {
        this.detailsLink = detailsLink;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<User> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<User> applicants) {
        this.applicants = applicants;
    }
}
