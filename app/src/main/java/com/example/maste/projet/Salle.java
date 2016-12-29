package com.example.maste.projet;

import java.util.Date;

/**
 * Created by maste on 06/10/2016.
 */

public class Salle {

    String name;
    String date;
    float degree;

    public Salle(String name,String date,float degree) { //Construtor Salle database Mesure.
        this.name = name;
        this.date = date;
        this.degree = degree;
    }

    public Salle(String name) {
        this.name = name;
    } //Construtor Salle database Salle.


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public float getDegree() {return degree;}

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }
}
