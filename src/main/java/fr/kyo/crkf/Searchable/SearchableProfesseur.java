package fr.kyo.crkf.Searchable;

import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;

public class SearchableProfesseur {
    private int id_professeur;
    private String nomEtPrenom;
    private int vehiculeCV;
    private Ville ville;

    public SearchableProfesseur() {
        this.id_professeur = 0;
        this.nomEtPrenom = "";
        this.vehiculeCV = 0;
        ville = new Ville(0, "", 0f, 0f, new Departement(0, "", ""));

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
        this.ville = ville;
    }

    public Ville getVille() {
        return ville;
    }
}
