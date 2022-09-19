package fr.kyo.crkf.Searchable;

import fr.kyo.crkf.Entity.*;
import fr.kyo.crkf.dao.DAOFactory;

import java.util.ArrayList;

public class Filter {

    private ArrayList<Classification> classifications;
    private ArrayList<Famille> familles;
    private ArrayList<Departement> departements;
    private ArrayList<Ville> villes;
    private ArrayList<Instrument> instruments;
    private ArrayList<Cycle> cycles;
    

    public Filter(){
        classifications = DAOFactory.getClassificationDAO().getAll(1);
        classifications.add(0,new Classification(0,"Classification"));

        familles = DAOFactory.getFamilleDAO().getAll(1);
        familles.add(0,new Famille(0,"Famille",0));

        villes = DAOFactory.getVilleDAO().getAll(1);
        villes.add(0, new Ville(0,"Ville", 0F, 0F, 0));

        departements = DAOFactory.getDepartementDAO().getAll(1);
        departements.add(0, new Departement(0,"", "Departement"));

        instruments = DAOFactory.getInstrumentDAO().getAll(1);
        instruments.add(0, new Instrument(0, "Instrument"));

        cycles = DAOFactory.getCycleDAO().getAll(1);
        cycles.add(0, new Cycle(0, "Cycle" , 0));
    }

    public ArrayList<Classification> getClassifications() {
        return classifications;
    }

    public ArrayList<Ville> getVilles() {
        return villes;
    }

    public ArrayList<Departement> getDepartements(){
        return departements;
    }
    public ArrayList<Famille> getFamilles() {
        return familles;
    }

    public ArrayList<Ville> getVilleLike(String text, int departement_id){
        ArrayList<Ville> villes = DAOFactory.getVilleDAO().getLike(text, departement_id);
        villes.add(0, new Ville(0,"Ville", 0F, 0F, 0));
        return villes;
    }

    public String getNumDepartement(int id){
        String numDepartement = DAOFactory.getDepartementDAO().getByID(id).getNumero_departement();
        return numDepartement;
    }

    public ArrayList<Ecole> getEcolesLike(String s) {
        SearchableEcole searchableEcole = new SearchableEcole();
        searchableEcole.setNom(s);
        ArrayList<Ecole> ecoles = DAOFactory.getEcoleDAO().getLike(searchableEcole,1);
        ecoles.add(new Ecole(0,"Ecole",0));
        return ecoles;
    }

    public ArrayList<Instrument> getInstrument() {
        return instruments;
    }

    public ArrayList<Cycle> getCycles(){
        return cycles;
    }
}