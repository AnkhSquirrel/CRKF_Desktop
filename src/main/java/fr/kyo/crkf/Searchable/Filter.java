package fr.kyo.crkf.Searchable;

import fr.kyo.crkf.Entity.Classification;
import fr.kyo.crkf.Entity.Famille;
import fr.kyo.crkf.dao.DAOFactory;

import java.util.ArrayList;

public class Filter {

    private ArrayList<Classification> classifications;
    private ArrayList<Famille> familles;

    public Filter(){
        classifications = DAOFactory.getClassificationDAO().getAll(1);
        classifications.add(0,new Classification(0,"Classification"));

        familles = DAOFactory.getFamilleDAO().getAll(1);
        familles.add(0,new Famille(0,"Famille",new Classification(0,"Classification")));
    }

    public ArrayList<Classification> getClassifications() {
        return classifications;
    }

    public ArrayList<Famille> getFamilles() {
        return familles;
    }
}
