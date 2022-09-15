package fr.kyo.crkf.Entity;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Ecole {
    private int id_ecole;
    private String nom;
    private int adresse;

    public Ecole(int id_ecole, String Nom ,int adresse) {
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
        return DAOFactory.getAdresseDAO().getByID(adresse);
    }
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse.getId_adresse();
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
