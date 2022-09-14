package fr.kyo.crkf.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Departement {
    private int id_departement;
    private String numero_departement;
    private String departement;
    private ArrayList<Ville> villes;

    public Departement(int id_departement, String numero_departement, String departement) {
        this.id_departement = id_departement;
        this.numero_departement = numero_departement;
        this.departement = departement;
        villes = new ArrayList<>();
    }

    public int getId_departement() {
        return id_departement;
    }

    public void setId_departement(int id_departement) {
        this.id_departement = id_departement;
    }

    public String getNumero_departement() {
        return numero_departement;
    }

    public void setNumero_departement(String numero_departement) {
        this.numero_departement = numero_departement;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }
    public ArrayList<Ville> getVille(){
        return villes;
    }
    @Override
    public String toString(){
        if(id_departement == 0)
            return  departement;
        else
            return numero_departement + " - " + departement;
    }
    public ObservableValue<String> getDepartementStringProperty(){
        return new SimpleStringProperty(departement);
    }
    public ObservableValue<String> getNumDepartementString(){
        return new SimpleStringProperty(numero_departement);
    }

    public ArrayList<Ville> getVilles() {
        return villes;
    }

}
