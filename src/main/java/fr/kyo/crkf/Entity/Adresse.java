package fr.kyo.crkf.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Adresse {
    private int id;
    private String adresse;
    private Ville ville;

    public Adresse(int id,String adresse, Ville ville) {
        this.id = id;
        this.adresse = adresse;
        this.ville = ville;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public Ville getVille() {
        return ville;
    }
    public void setVille(Ville ville) {
        this.ville = ville;
    }
    public ObservableValue<String> getAdresseStringProperty(){
        return new SimpleStringProperty(adresse);
    }
}
