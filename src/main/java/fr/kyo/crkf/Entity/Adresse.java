package fr.kyo.crkf.Entity;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Adresse {
    private int id_adresse;
    private String adresse;
    private int ville;

    public Adresse(int id_adresse, String adresse, int ville) {
        this.id_adresse = id_adresse;
        this.adresse = adresse;
        this.ville = ville;
    }
    public int getId_adresse() {
        return id_adresse;
    }
    public void setId_adresse(int id_adresse) {
        this.id_adresse = id_adresse;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public Ville getVille() {
        return DAOFactory.getVilleDAO().getByID(ville);
    }
    public void setVille(Ville ville) {
        this.ville = ville.getId_ville();
    }
    public void setVille(int ville) {
        this.ville = ville;
    }
    public ObservableValue<String> getAdresseStringProperty(){
        return new SimpleStringProperty(adresse);
    }
    @Override
    public String toString(){
        return adresse;
    }
}
