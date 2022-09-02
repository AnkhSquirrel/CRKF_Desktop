package fr.kyo.crkf.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Departement {
    private int id_departement;
    private String departement;
    private ArrayList<Ville> villes;

    public Departement(int id_departement, String departement) {
        this.id_departement = id_departement;
        this.departement = departement;
        villes = new ArrayList<>();
    }
    public int getId_departement() {
        return id_departement;
    }
    public void setId_departement(int id_departement) {
        this.id_departement = id_departement;
    }
    public String getDepartement() {
        return departement;
    }
    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public ObservableValue<String> getDepartementStringProperty(){
        return new SimpleStringProperty(departement);
    }

    public ArrayList<Ville> getVilles() {
        return villes;
    }

    @Override
    public String toString(){
        return departement;
    }
}
