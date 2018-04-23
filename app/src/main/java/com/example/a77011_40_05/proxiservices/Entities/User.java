package com.example.a77011_40_05.proxiservices.Entities;

/**
 * Created by 77011-40-05 on 08/03/2018.
 */

public class User {

    private int idUser;
    private String name;
    private String firstname;
    private String path;

    public User(){

    }

    public int getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFullName(){
        return name +" "+ firstname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
