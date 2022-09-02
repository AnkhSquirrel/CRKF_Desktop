package fr.kyo.crkf.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;


public class Ville {
    private int id_ville;
    private String ville;
    private float longitude;
    private float latitude;
    private Departement departement;

    public Ville(int id_ville, String ville,float longitude, float latitude ,Departement departement) {
        this.id_ville = id_ville;
        this.ville = ville;
        this.longitude = longitude;
        this.latitude = latitude;
        this.departement = departement;
    }
    public int getId_ville() {
        return id_ville;
    }
    public void setId_ville(int id_ville) {
        this.id_ville = id_ville;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public float getLongitude() {
        return longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    public Departement getDepartement() {
        return departement;
    }
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
    @Override
    public String toString(){
        return ville;
    }
    public ObservableValue<String> getVilleStringProperty(){
        return new SimpleStringProperty(ville);
    }
}
