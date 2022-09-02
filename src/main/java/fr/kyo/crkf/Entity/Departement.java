package fr.kyo.crkf.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Departement {
    private int id_departement;
    private String departement;

    public Departement(int id_departement, String departement) {
        this.id_departement = id_departement;
        this.departement = departement;
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
    @Override
    public String toString(){
        return departement;
    }
    public ObservableValue<String> getDepartementStringProperty(){
        return new SimpleStringProperty(departement);
    }
}
