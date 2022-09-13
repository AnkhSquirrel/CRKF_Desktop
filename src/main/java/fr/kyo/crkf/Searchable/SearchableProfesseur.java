package fr.kyo.crkf.Searchable;

import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;
import fr.kyo.crkf.dao.DAOFactory;

public class SearchableProfesseur {
    private int id_professeur;
    private String nomEtPrenom;
    private int vehiculeCV;
    private int ville;
    private int departement;

    public SearchableProfesseur() {
        this.id_professeur = 0;
        this.nomEtPrenom = "";
        this.vehiculeCV = 0;
        ville = 0;
        departement = 0;

    }

    public String getNomEtPrenom() {
        return nomEtPrenom;
    }

    public void setNomEtPrenom(String nomEtPrenom) {
        this.nomEtPrenom = nomEtPrenom;
    }

    public Integer getVehiculeCV() {
        return vehiculeCV;
    }

    public void setVille(Ville ville) {
        this.ville = ville.getId_ville();
    }

    public Ville getVille() {
        return DAOFactory.getVilleDAO().getByID(ville);
    }
    public int getVilleId() {
        return ville;
    }

    public int getDepartementId() {
        return departement;
    }

    public void setDepartementId(int departement) {
        this.departement = departement;
    }
}
