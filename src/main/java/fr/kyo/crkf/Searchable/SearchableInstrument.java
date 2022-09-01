package fr.kyo.crkf.Searchable;

import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Famille;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;


public class SearchableInstrument {
    private int id_instrument;
    private String nom;
    private Famille famille;

    public SearchableInstrument() {
        this.id_instrument = id_instrument;
        this.nom = nom;
        famille = new Famille(0,"",new Classification(0,""));
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
        return famille;
    }
    public void setFamille(Famille famille) {
        this.famille = famille;
    }
    public ObservableValue<String> getNomStringProperty(){
        return new SimpleStringProperty(nom);
    }
}
