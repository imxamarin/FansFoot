package com.fansfoot.fansfoot.models;

/**
 * Created by xamarin on 16/01/17.
 */

public class ImageData {
    public String type="";
    public String USERID="";
    public String full_name="";
    public String profilepicture="";
    public String extension="";
    public String country="";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
