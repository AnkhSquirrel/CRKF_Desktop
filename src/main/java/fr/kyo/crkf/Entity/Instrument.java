package fr.kyo.crkf.Entity;

import fr.kyo.crkf.dao.DAOFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Instrument {
    private int id_instrument;
    private String nom;
    private ArrayList<Integer> familles;

    public Instrument(int id_instrument, String nom) {
        this.id_instrument = id_instrument;
        this.nom = nom;
        familles = new ArrayList<>();
    }
    public int getId_instrument() {
        return id_instrument;
    }
    public void setId_instrument(int id_instrument) {
        this.id_instrument = id_instrument;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public ArrayList<Famille> getFamilles() {
        ArrayList<Famille> list = new ArrayList<>();
        for(int i : familles){
            list.add(DAOFactory.getFamilleDAO().getByID(i));
        }
        return list;
    }
    public void setFamilles(ArrayList<Famille> familles) {
        this.familles.clear();
        for(Famille famille : familles)
            this.familles.add(famille.getId_famille());
    }
    public void addFamille(Famille famille){
        familles.add(famille.getId_famille());
    }
    public ObservableValue<String> getNomStringProperty(){
        return new SimpleStringProperty(nom);
    }
}
