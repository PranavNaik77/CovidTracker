package com.tracker.CovidTracker.models;

public class locationstat {
    private String country;
    private String state;
    private long ConfirmedCases;
    private String city;
    private String Last_Update;
    private long Deaths;
    private long todayscases;

    public long getTodayscases() {
        return todayscases;
    }

    public void setTodayscases(long todayscases) {
        this.todayscases = todayscases;
    }



    public String getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(String last_Update) {
        Last_Update = last_Update;
    }

    public long getDeaths() {
        return Deaths;
    }

    public void setDeaths(long deaths) {
        Deaths = deaths;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getConfirmedCases() {
        return ConfirmedCases;
    }

    public void setConfirmedCases(long ConfirmedCases) {
        this.ConfirmedCases = ConfirmedCases;
    }

    @Override
    public String toString() {
        return "locationstat{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", ConfirmedCases=" + ConfirmedCases +
                ", city='" + city + '\'' +
                ", Last_Update='" + Last_Update + '\'' +
                ", Deaths=" + Deaths +
                '}';
    }
}
