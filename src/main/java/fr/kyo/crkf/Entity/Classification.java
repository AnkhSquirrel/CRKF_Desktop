package fr.kyo.crkf.Entity;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Classification {
    private int id_classification;
    private String classification;

    public Classification(int id_classification, String classification) {
        this.id_classification = id_classification;
        this.classification = classification;
    }
    public int getId_classification() {
        return id_classification;
    }
    public void setId_classification(int id_classification) {
        this.id_classification = id_classification;
    }
    public String getclassification() {
        return classification;
    }
    public void setclassification(String classification) {
        this.classification = classification;
    }
    public ObservableValue<String> getClassificationStringProperty(){
        return new SimpleStringProperty(classification);
    }
    public ObservableValue<Integer> getNumberFamilles(){
        return new ReadOnlyObjectWrapper<>(DAOFactory.getFamilleDAO().getByClassification(id_classification).size());
    }
    @Override
    public String toString() {
        return classification;
    }
}
