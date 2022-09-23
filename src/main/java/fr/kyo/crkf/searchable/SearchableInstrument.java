package fr.kyo.crkf.searchable;

import fr.kyo.crkf.entity.Classification;
import fr.kyo.crkf.entity.Famille;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class SearchableInstrument {

    private String nom;
    private int famille;
    private int classification;

    public SearchableInstrument() {
        this.nom = "";
        famille = 0;
        classification = 0;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Famille getFamille() {
        return DAOFactory.getFamilleDAO().getByID(famille);
    }

    public int getFamilleId(){
        return famille;
    }

    public void setFamille(Famille famille) {
        this.famille = famille.getFamilleId();
    }

    public void setClassification(Classification classification){
        this.classification = classification.getClassificationId();
    }

    public void setClassificationId(int classification){
        this.classification = classification;
    }

    public Classification getClassification(){
        return DAOFactory.getClassificationDAO().getByID(classification);
    }

    public int getClassificationId(){
        return classification;
    }

    public ObservableValue<String> getNomStringProperty(){
        return new SimpleStringProperty(nom);
    }
}
