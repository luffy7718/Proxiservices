package com.example.a77011_40_05.proxiservices.Entities;

import static com.example.a77011_40_05.proxiservices.Utils.Constants._LOGO_LIST;

/**
 * Created by 77011-40-05 on 15/03/2018.
 */

public class CategoryPrestation {

    String name;
    int id;
    String imgName;

    public CategoryPrestation(String name, int id) {
        this.name = name;
        this.id = id;
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
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
