package com.example.a77011_40_05.proxiservices.Entities;

import android.util.Log;

import com.example.a77011_40_05.proxiservices.Utils.Constants;

import static com.example.a77011_40_05.proxiservices.Utils.Constants._LOGO_LIST;

/**
 * Created by 77011-40-05 on 15/03/2018.
 */

public class CategoryPrestation {

    String name;
    int idCategoryPrestation;
    String imgName;
    String description;

    public CategoryPrestation(String name, int id) {
        this.name = name;
        this.idCategoryPrestation = id;
        if( id< _LOGO_LIST.length){
            this.imgName = _LOGO_LIST[id];
        }else{
            this.imgName = "";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return idCategoryPrestation;
    }

    public void setId(int id) {
        this.idCategoryPrestation = id;
    }

    public String getImgName() {
        if(imgName == null){
            if( this.idCategoryPrestation< _LOGO_LIST.length){
                this.imgName = _LOGO_LIST[this.idCategoryPrestation];
            }else{
                this.imgName = "";
            }
        }
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
