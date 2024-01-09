package com.example.internexpress.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity<Long>{
    private String firstName;
    private String lastName;
    private String date;
    private String gender;
    private String password;
    private String email;
    private String graduatedFrom;
    private List<String>  interestedAreas;

    private String companyDetails;
    private String companyLink;

    private String companyName;



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", date='" + date + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", graduatedFrom='" + graduatedFrom + '\'' +
                ", interestedAreas=" + interestedAreas +
                ", userType='" + userType + '\'' +
                '}';
    }

    private String userType;
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }



    public void setGraduatedFrom(String graduatedFrom) {
        this.graduatedFrom = graduatedFrom;
    }

    public void setInterestedAreas(List<String> interestedAreas) {
        this.interestedAreas = interestedAreas;
    }

    //user simplu
    public User(String firstName, String lastName, String date, String gender, String email, String password,String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }
    public User(String firstName, String lastName, String date, String gender, String email, String password,String graduatedFrom,
                List<String>  interestedAreas, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.graduatedFrom = graduatedFrom;
        this.interestedAreas = interestedAreas;
        this.userType = userType;
    }

    public User(String firstName, String lastName, String date, String gender, String password, String email, String companyName, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.companyName = companyName;
        this.userType = userType;
    }

    public User(String firstName, String lastName, String date, String gender, String email, String userType, String companyName, String companyDetails, String companyLink) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.gender = gender;
        //this.password = password;
        this.email = email;
        this.companyName = companyName;
        this.userType = userType;
        this.companyDetails = companyDetails;
        this.companyLink= companyLink;
    }

    public User() {

    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(String companyDetails) {
        this.companyDetails = companyDetails;
    }

    public String getCompanyLink() {
        return companyLink;
    }

    public void setCompanyLink(String companyLink) {
        this.companyLink = companyLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() { return password; }

    public String getDate() {
        return date;
    }

    public String getGender() {
        return gender;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) { this.password = password; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(date, user.date) && Objects.equals(gender, user.gender) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, date, gender, password, email);
    }

    public String getGraduatedFrom() {
        return graduatedFrom;
    }

    public List<String> getInterestedAreas() {
        return interestedAreas;
    }
}
