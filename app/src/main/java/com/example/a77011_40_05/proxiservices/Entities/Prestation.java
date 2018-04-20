package com.example.a77011_40_05.proxiservices.Entities;

public class Prestation {

    int idPrestation;
    int idUser;
    int idCategoryPerstation;
    String name;
    String firstname;
    String description;
    String path;

    public int getIdPrestation() {
        return idPrestation;
    }

    public void setIdPrestation(int idPrestation) {
        this.idPrestation = idPrestation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCategoryPerstation() {
        return idCategoryPerstation;
    }

    public void setIdCategoryPerstation(int idCategoryPerstation) {
        this.idCategoryPerstation = idCategoryPerstation;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
