package fr.kyo.crkf.searchable;

import fr.kyo.crkf.entity.*;
import fr.kyo.crkf.dao.DAOFactory;
import java.util.List;

public class Filter {

    private final List<Classification> classifications;
    private final List<Famille> familles;
    private final List<Departement> departements;
    private final List<Ville> villes;
    private final List<Instrument> instruments;
    private final List<Cycle> cycles;

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

    public List<Classification> getClassifications() {
        return classifications;
    }

    public List<Departement> getDepartements(){
        return departements;
    }
    public List<Famille> getFamilles() {
        return familles;
    }

    public List<Instrument> getInstrument() {
        return instruments;
    }

    public List<Cycle> getCycles(){
        return cycles;
    }

    public List<Ville> getVilleLike(String text, int departementId){
        List<Ville> villesLike = DAOFactory.getVilleDAO().getLike(text, departementId);
        villesLike.add(0, new Ville(0,"Ville", 0F, 0F, 0));
        return villesLike;
    }

    public List<Ecole> getEcolesLike(String nomEcole) {
        SearchableEcole searchableEcole = new SearchableEcole();
        searchableEcole.setNom(nomEcole);
        List<Ecole> ecoles = DAOFactory.getEcoleDAO().getLike(searchableEcole,1);
        ecoles.add(new Ecole(0,"Ecole",0));
        return ecoles;
    }

}