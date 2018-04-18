package com.example.a77011_40_05.proxiservices.Entities;

/**
 * Created by 77011-40-05 on 08/03/2018.
 */

public class User {

    private int idUser;
    private String name;
    private String firstname;
    private String login;
    private String password;
    private String path;
    private String profilePic = null;

    public User(){

    }

    public User(String login, String password){
        this.login = login;
        this.password = password;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName(){
        return name +" "+ firstname;
    }


    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
