package com.example.a77011_40_05.proxiservices.Entities;



import com.example.a77011_40_05.proxiservices.Utils.Constants;

import java.util.Date;

public class Erreur {
    String message;
    String source;
    String method;
    String className;
    String date;
    String applicationName;
    String lineNumber;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Erreur() {
    }

    public Erreur(String message, String method, String className) {
        this.message = message;
        this.method = method;
        this.className = className;
    }

    public Erreur(Exception e) {

        Date date = new Date();

        this.applicationName = Constants.APP_NAME;
        this.source = "Android";
        this.date = date.toString();
        this.className = e.getStackTrace()[0].getClassName();
        this.method = e.getStackTrace()[0].getMethodName();
        this.lineNumber = String.valueOf(e.getStackTrace()[0].getLineNumber());
        this.message = e.getMessage();
    }
}
