package fr.kyo.crkf.entity;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Cycle {
    private final int cycleId;
    private String cycleLibelle;
    private int cycleNumero;

    public Cycle(int cycleId, String cycleLibelle, int cycleNumero) {
        this.cycleId = cycleId;
        this.cycleLibelle = cycleLibelle;
        this.cycleNumero = cycleNumero;
    }

    public int getCycleId() {
        return cycleId;
    }

    public String getCycleLibelle() {
        return cycleLibelle;
    }

    public void setCycleLibelle(String cycleLibelle) {
        this.cycleLibelle = cycleLibelle;
    }

    public int getCycleNumero() {
        return cycleNumero;
    }

    public void setCycleNumero(int cycleNumero) {
        this.cycleNumero = cycleNumero;
    }

    public ObservableValue<String> getCycleStringProperty(){
        return new SimpleStringProperty(String.valueOf(cycleLibelle));
    }

    public ObservableValue<Integer> getHighestCycle(){
        return new ReadOnlyObjectWrapper<>(DAOFactory.getCycleDAO().getHighestCycle());
    }

    @Override
    public String toString() {
        return cycleLibelle;
    }
}
