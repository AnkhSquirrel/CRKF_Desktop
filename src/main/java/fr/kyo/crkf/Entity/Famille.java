package fr.kyo.crkf.Entity;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Famille {
    private int id_famille;
    private String famille;
    private int classification;

    public Famille(int id_famille, String famille, int classification) {
        this.id_famille = id_famille;
        this.famille = famille;
        this.classification = classification;
    }
    public int getId_famille() {
        return id_famille;
    }
    public void setId_famille(int id_famille) {
        this.id_famille = id_famille;
    }
    public String getfamille() {
        return famille;
    }
    public void setfamille(String famille) {
        this.famille = famille;
    }
    public Classification getclassification() {
        return DAOFactory.getClassificationDAO().getByID(classification);
    }
    public void setclassification(Classification classification) {
        this.classification = classification.getId_classification();
    }
    public void setclassification(int classification) {
        this.classification = classification;
    }
    public ObservableValue<String> getFamilleStringProperty(){
        return new SimpleStringProperty(famille);
    }

    @Override
    public String toString() {
        return famille;
    }

    public int getId_classification() {
        return classification;
    }
}
