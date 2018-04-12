package com.example.a77011_40_05.proxiservices.Entities;

/**
 * Created by 77011-40-05 on 08/03/2018.
 */

public class User {

    private int idUser;
    private String nom;
    private String prenom;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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
        return nom+" "+prenom;
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
