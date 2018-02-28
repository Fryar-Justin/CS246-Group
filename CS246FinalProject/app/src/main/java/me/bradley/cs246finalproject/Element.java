package me.bradley.cs246finalproject;

/**
 * Created by Chris on 2/27/2018.
 */

public class Element {
    private int protons = 0;
    private int neutrons;
    private int electrons;
    private String name;

    // getters & setters
    public int getProtons(){
        return protons;
    }

    public int getNeutrons(){
        return neutrons;
    }

    public int getElectrons(){
        return electrons;
    }

    public String getName(){
        return name;
    }

    public void setProtons(int protons){
        this.protons = protons;
    }

    public void setNeutrons(int neutrons){
        this.neutrons = neutrons;
    }

    public void setElectrons(int electrons){
        this.electrons = electrons;
    }

    public void setName(String name){
        this.name = name;
    }
}
