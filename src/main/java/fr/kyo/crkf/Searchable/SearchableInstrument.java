package fr.kyo.crkf.Searchable;

import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;


public class SearchableInstrument {
    private int id_instrument;
    private String nom;
    private int famille;
    private int classification;

    public SearchableInstrument() {
        this.id_instrument = 0;
        this.nom = "";
        famille = 0;
        classification = 0;
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
    public Famille getFamille() {
        return DAOFactory.getFamilleDAO().getByID(famille);
    }
    public int getFamilleId(){return famille;}
    public void setFamille(Famille famille) {
        this.famille = famille.getId_famille();
    }
    public void setClassification(Classification classification){this.classification = classification.getId_classification();}
    public void setClassificationId(int classification){this.classification = classification;}
    public Classification getClassification(){return DAOFactory.getClassificationDAO().getByID(classification);}
    public int getClassificationId(){return classification;}
    public ObservableValue<String> getNomStringProperty(){
        return new SimpleStringProperty(nom);
    }
}
