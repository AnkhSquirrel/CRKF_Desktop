package fr.kyo.crkf.entity;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Departement {

    private final int departementId;
    private String departementNumero;
    private String departementLibelle;

    public Departement(int departementId, String departementNumero, String departementLibelle) {
        this.departementId = departementId;
        this.departementNumero = departementNumero;
        this.departementLibelle = departementLibelle;
    }

    public int getDepartementId() {
        return departementId;
    }

    public String getDepartementNumero() {
        return departementNumero;
    }

    public void setDepartementNumero(String departementNumero) {
        this.departementNumero = departementNumero;
    }

    public String getDepartementLibelle() {
        return departementLibelle;
    }

    public void setDepartementLibelle(String departementLibelle) {
        this.departementLibelle = departementLibelle;
    }

    public ObservableValue<String> getDepartementStringProperty(){
        return new SimpleStringProperty(departementLibelle);
    }

    public ObservableValue<String> getNumDepartementString(){
        return new SimpleStringProperty(departementNumero);
    }

    public ObservableValue<Integer> getNumberOfSchoolInDepartement(){
        return new ReadOnlyObjectWrapper<>(DAOFactory.getEcoleDAO().getByDepartement(departementId).size());
    }

    @Override
    public String toString(){
        return departementId == 0 ? departementLibelle : departementNumero + " - " + departementLibelle;
    }
}
