package fr.kyo.crkf.Entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Personne {
    private int id_personne;
    private int id_ecole;
    private String nom;
    private String prenom;
    private int vehiculeCv;
    private Adresse adresse;
    private Ecole ecole;

    private ArrayList<Diplome> diplomes;

    public Personne() {
        this.id_personne = id_personne;
        this.nom = nom;
        this.prenom = prenom;
        diplomes = new ArrayList<>();
    }

    public Personne(int id_personne, String nom, String prenom, int vehiculeCv, Adresse adresse, Ecole ecole) {
        this.id_personne = id_personne;
        this.nom = nom;
        this.prenom = prenom;
        this.vehiculeCv = vehiculeCv;
        this.adresse = adresse;
        this.ecole = ecole;
        diplomes = new ArrayList<>();
    }
    public int getId_personne() {
        return id_personne;
    }
    public void setId_personne(int id_personne) {
        this.id_personne = id_personne;
    }
    public void setId_ecole(int id_ecole){
        this.id_ecole = id_ecole;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public int getVehiculeCv() {
        return vehiculeCv;
    }
    public void setVehiculeCv(int vehiculeCv) {
        this.vehiculeCv = vehiculeCv;
    }
    public Adresse getAdresse() {
        return adresse;
    }
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
    public Ecole getEcole() {
        return ecole;
    }
    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
    }
    public ArrayList<Diplome> getDiplomes() {
        return diplomes;
    }
    public void setDiplomes(ArrayList<Diplome> diplomes) {
        this.diplomes = diplomes;
    }
    public void addDiplome(Diplome diplome){
        diplomes.add(diplome);
    }
    public ObservableValue<String> getNomStringProperty(){
        return new SimpleStringProperty(nom);
    }
    public ObservableValue<String> getPrenomStringProperty(){
        return new SimpleStringProperty(prenom);
    }
    public IntegerProperty getVehiculeCVIntegerProperty(){
        return new SimpleIntegerProperty(vehiculeCv);
    }
}
