package com.example.daniel.bikesharing.ObjectDB;

import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import com.example.daniel.bikesharing.R;

/**
 * Created by Daniel on 11.04.2017.
 */

public class Canton {
    private int id;
    private String name;
    private String picture;

    // contructors

    public  Canton(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    public  Canton(int id, String name, String picture){
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    // getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
