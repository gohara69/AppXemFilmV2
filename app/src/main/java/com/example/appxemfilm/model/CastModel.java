package com.example.appxemfilm.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CastModel implements Serializable {
    private int id;
    private String name;
    private String profile_path;
    private int cast_id;
    private String character;
    @SerializedName("also_known_as")
    private String also_known_as;
    @SerializedName("biography")
    private String biography;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("gender")
    private int gender;
    @SerializedName("known_for_department")
    private String known_for_department;
    @SerializedName("place_of_birth")
    private String place_of_birth;
    @SerializedName("homepage")
    private String homepage;
    public CastModel() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(String also_known_as) {
        this.also_known_as = also_known_as;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public CastModel(int id, String name, String profile_path, int cast_id, String character) {
        this.id = id;
        this.name = name;
        this.profile_path = profile_path;
        this.cast_id = cast_id;
        this.character = character;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public int getCast_id() {
        return cast_id;
    }

    public String getCharacter() {
        return character;
    }
}