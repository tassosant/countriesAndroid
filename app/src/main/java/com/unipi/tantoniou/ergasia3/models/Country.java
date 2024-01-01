package com.unipi.tantoniou.ergasia3.models;

public class Country {



    private int id;

    private String name;

    private String population;

    private String capital;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }



}
