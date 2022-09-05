package fr.kyo.crkf.Searchable;

import fr.kyo.crkf.Entity.*;
import fr.kyo.crkf.dao.DAOFactory;

import java.util.ArrayList;

public class Filter {

    private ArrayList<Classification> classifications;
    private ArrayList<Famille> familles;
    private ArrayList<Departement> departements;
    private ArrayList<Ville> villes;

    public Filter(){
        classifications = DAOFactory.getClassificationDAO().getAll(1);
        classifications.add(0,new Classification(0,"Classification"));

        familles = DAOFactory.getFamilleDAO().getAll(1);
        familles.add(0,new Famille(0,"Famille",new Classification(0,"Classification")));

        villes = DAOFactory.getVilleDAO().getAll(1);
        villes.add(0, new Ville(0,"Ville", 0F, 0F, new Departement(0, "", "Departement")));

        departements = DAOFactory.getDepartementDAO().getAll(1);
        departements.add(0, new Departement(0,"", "Departement"));
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
}