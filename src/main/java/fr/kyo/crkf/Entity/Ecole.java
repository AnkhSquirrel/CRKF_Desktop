package fr.kyo.crkf.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Ecole {
    private int id_ecole;
    private String nom;
    private Adresse adresse;

    public Ecole(int id_ecole, String Nom ,Adresse adresse) {
        this.id_ecole = id_ecole;
        this.nom = Nom;
        this.adresse = adresse;
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

    public ObservableValue<String> getNomStringProperty(){
        return new SimpleStringProperty(nom);
    }
}
