package fr.kyo.crkf.Searchable;

import fr.kyo.crkf.Entity.Adresse;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class SearchableVille {
    private String nom;

    private Departement departement;

    public SearchableVille() {
        this.nom = nom;
        departement = new Departement(0, "Departement", "");
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Departement getDepartement() {
        return departement;
    }
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
    public ObservableValue<String> getNomStringProperty(){
        return new SimpleStringProperty(nom);
    }
}
