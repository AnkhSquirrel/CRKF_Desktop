package fr.kyo.crkf.entity;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import java.util.ArrayList;
import java.util.List;

public class Personne {

    private int personneId;
    private String personneNom;
    private String personnePrenom;
    private int vehiculeCv;
    private int adresseId;
    private int ecoleID;
    private List<Diplome> diplomes;
    private List<Famille> familles;
    public Personne() {
        this.personneId = 0;
        this.personneNom = "";
        this.personnePrenom = "";
        diplomes = new ArrayList<>();
        familles = new ArrayList<>();
    }

    public Personne(int personneId, String nom, String prenom, int vehiculeCv, int adresse, int ecole) {
        this.personneId = personneId;
        this.personneNom = nom;
        this.personnePrenom = prenom;
        this.vehiculeCv = vehiculeCv;
        this.adresseId = adresse;
        this.ecoleID = ecole;
        diplomes = new ArrayList<>();
        familles = new ArrayList<>();
    }
    public int getPersonneId() {
        return personneId;
    }

    public void setPersonneId(int personneId) {
        this.personneId = personneId;
    }

    public String getPersonneNom() {
        return personneNom;
    }

    public void setPersonneNom(String personneNom) {
        this.personneNom = personneNom;
    }

    public String getPersonnePrenom() {
        return personnePrenom;
    }

    public void setPersonnePrenom(String personnePrenom) {
        this.personnePrenom = personnePrenom;
    }

    public int getVehiculeCv() {
        return vehiculeCv;
    }

    public void setVehiculeCv(int vehiculeCv) {
        this.vehiculeCv = vehiculeCv;
    }

    public Adresse getAdresseId() {
        return DAOFactory.getAdresseDAO().getByID(adresseId);
    }

    public void setAdresseId(Adresse adresseId) {
        this.adresseId = adresseId.getAdresseId();
    }

    public Ecole getEcoleID() {
        return DAOFactory.getEcoleDAO().getByID(ecoleID);
    }

    public void setEcoleID(Ecole ecoleID) {
        this.ecoleID = ecoleID.getEcoleId();
    }

    public List<Diplome> getDiplomes() {
        return diplomes;
    }
    public List<Famille> getFamilles() { return familles; }
    public void setFamilles(List<Famille> familles ) { this.familles = familles; }

    public void setDiplomes(List<Diplome> diplomes) {
        this.diplomes = diplomes;
    }

    public void addDiplome(Diplome diplome){
        diplomes.add(diplome);
    }

    public ObservableValue<String> getNomStringProperty(){
        return new SimpleStringProperty(personneNom);
    }

    public ObservableValue<String> getPrenomStringProperty(){
        return new SimpleStringProperty(personnePrenom);
    }
}
