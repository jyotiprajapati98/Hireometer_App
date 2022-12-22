package com.example.jobhub.Models;

import android.net.Uri;

import java.io.Serializable;
import java.net.URI;

public class PassToVerification implements Serializable {
    private String recordNum, name, email, mobile, userType;
    private String resume;

    private String jobType, offerSal, desingnation, company_name, company_website;

    public PassToVerification(){

    }
    public PassToVerification(String name, String email, String mobile, String userType){
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.userType = userType;

        System.out.println("Inside the PassToVerfication class "+name+" "+email + " "+ " "+mobile+ " "+userType);
    }

    //add record number
    public PassToVerification(String recordNum, String name, String email, String mobile, String userType) {
        this.recordNum=recordNum;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.userType = userType;
        System.out.println("Inside the PassToVerfication class "+name+" "+email + " "+ " "+mobile+ " "+userType);

    }

    //add resume
    public PassToVerification(String recordNum, String name, String email, String mobile, String userType,String resume){
        this.recordNum=recordNum;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.userType = userType;
        this.resume=resume;

        System.out.println("Inside the PassToVerfication class "+name+" "+email + " "+ " "+mobile+ " "+userType+" "+resume);
    }

    //for hire
    public PassToVerification(String recordNum, String name, String email, String mobile, String userType,String desingnation, String company_name, String company_website){
        this.recordNum=recordNum;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.userType = userType;
        //this.jobType = jobType;
        //this.offerSal = offerSal;
        this.desingnation = desingnation;
        this.company_name = company_name;
        this.company_website=company_website;

        System.out.println("Inside the PassToVerfication class "+name+" "+email + " "+ " "+mobile+ " "+userType);
    }


    public String getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }

    public PassToVerification(String resume) {
        this.resume=resume;
    }

    public String getResume(){
        return resume;
    }
    public void setResume(String resume){
        this.resume=resume;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_website() {
        return company_website;
    }

    public void setCompany_website(String company_website) {
        this.company_website = company_website;
    }

    public String getDesingnation() {
        return desingnation;
    }

    public void setDesingnation(String desingnation) {
        this.desingnation = desingnation;
    }

    public String getOfferSal() {
        return offerSal;
    }

    public void setOfferSal(String offerSal) {
        this.offerSal = offerSal;
    }
}
