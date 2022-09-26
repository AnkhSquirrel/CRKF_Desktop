package fr.kyo.crkf.entity;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import java.util.ArrayList;
import java.util.List;

public class Instrument {
    private final int instrumentId;
    private String instrumentLibelle;
    private final List<Integer> familles;

    public Instrument(int instrumentId, String instrumentLibelle) {
        this.instrumentId = instrumentId;
        this.instrumentLibelle = instrumentLibelle;
        familles = new ArrayList<>();
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public String getInstrumentLibelle() {
        return instrumentLibelle;
    }

    public void setInstrumentLibelle(String instrumentLibelle) {
        this.instrumentLibelle = instrumentLibelle;
    }

    public List<Famille> getFamilles() {
        ArrayList<Famille> list = new ArrayList<>();
        for(int i : familles)
            list.add(DAOFactory.getFamilleDAO().getByID(i));
        return list;
    }

    public void addFamille(Famille famille){
        familles.add(famille.getFamilleId());
    }

    public ObservableValue<String> getNomStringProperty(){
        return new SimpleStringProperty(instrumentLibelle);
    }

    @Override
    public String toString() {
        return instrumentLibelle;
    }
}
