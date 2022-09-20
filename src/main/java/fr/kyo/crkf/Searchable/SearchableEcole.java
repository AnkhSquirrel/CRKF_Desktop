package fr.kyo.crkf.Searchable;

import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.dao.DAOFactory;

public class SearchableEcole {

    private String nom;
    private int ville;
    private int departement;

    public SearchableEcole() {
        this.nom = "";
        ville = 0;
        departement = 0;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getIdDepartement() {
        return departement;
    }
    public Departement getDepartement(){
        return DAOFactory.getDepartementDAO().getByID(departement);
    }
    public void setIdDepartement(int departement) {
        this.departement = departement;
    }
    public int getIdVille() {
        return ville;
    }
    public Ville getVille(){
        return DAOFactory.getVilleDAO().getByID(ville);
    }
    public void setIdVille(int ville) {
        this.ville = ville;
    }
}
