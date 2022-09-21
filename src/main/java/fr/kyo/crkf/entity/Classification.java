package fr.kyo.crkf.entity;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Classification {
    private final int classificationId;
    private String classificationLibelle;

    public Classification(int classificationId, String classificationLibelle) {
        this.classificationId = classificationId;
        this.classificationLibelle = classificationLibelle;
    }

    public int getClassificationId() {
        return classificationId;
    }

    public String getClassificationLibelle() {
        return classificationLibelle;
    }

    public void setClassificationLibelle(String classificationLibelle) {
        this.classificationLibelle = classificationLibelle;
    }

    public ObservableValue<String> getClassificationStringProperty(){
        return new SimpleStringProperty(classificationLibelle);
    }

    public ObservableValue<Integer> getNumberFamilles(){
        return new ReadOnlyObjectWrapper<>(DAOFactory.getFamilleDAO().getByClassification(classificationId).size());
    }

    @Override
    public String toString() {
        return classificationLibelle;
    }
}
