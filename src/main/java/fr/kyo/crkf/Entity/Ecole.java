package fr.kyo.crkf.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Ecole {
    private int id_ecole;
    private String nom;
    private Adresse adresse;

    private ArrayList<Ville> villes;

    public Ecole(int id_ecole, String Nom ,Adresse adresse) {
        this.id_ecole = id_ecole;
        this.nom = Nom;
        this.adresse = adresse;
        villes = new ArrayList<>();
    }
    public int getId_ecole() {
        return id_ecole;
    }
    public void setId_ecole(int id_ecole) {
        this.id_ecole = id_ecole;
    }
    public Adresse getAdresse() {
        return adresse;
    }
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void addVille(Ville ville){
        villes.add(ville);
    }

    public ObservableValue<String> getNomStringProperty(){
        return new SimpleStringProperty(nom);
    }
}
