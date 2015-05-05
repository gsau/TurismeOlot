package com.example.marc.turismeolot.Model;

import java.io.Serializable;

/**
 * Created by Marc on 27/03/2015.
 */
public class Lloc implements Serializable {
    private String titol, descripcio;
    private int imatge;


    public Lloc(String titol, String descripcio, int imatge) {
        this.titol = titol;
        this.descripcio = descripcio;
        this.imatge=imatge;

    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getImatge() {
        return imatge;
    }

    public void setImatge(int imatge) {
        this.imatge = imatge;
    }
}