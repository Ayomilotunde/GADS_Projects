package com.example.gadsprojects;

import java.io.Serializable;

public class Learners implements Serializable {

    public Integer hours,score;
    private int id;
    public String name;
    public String country;
    public String badgeUrl;


    public Learners(Integer hours, Integer score, int id, String name, String country, String badgeUrl) {
        this.hours = hours;
        this.score = score;
        this.id = id;
        this.name = name;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }
}
