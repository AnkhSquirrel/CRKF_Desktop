package fr.kyo.crkf.Entity;

import java.util.ArrayList;

public class Instrument {
    private int id_instrument;
    private String nom;
    private ArrayList<Famille> familles;

    public Instrument(int id_instrument, String nom, Famille famille) {
        this.id_instrument = id_instrument;
        this.nom = nom;
        familles = new ArrayList<>();
    }
    public int getId_instrument() {
        return id_instrument;
    }
    public void setId_instrument(int id_instrument) {
        this.id_instrument = id_instrument;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public ArrayList<Famille> getFamilles() {
        return familles;
    }
    public void setFamilles(ArrayList<Famille> familles) {
        this.familles = familles;
    }
}
