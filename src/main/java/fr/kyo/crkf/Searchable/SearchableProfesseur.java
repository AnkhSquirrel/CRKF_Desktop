package fr.kyo.crkf.Searchable;

public class SearchableProfesseur {
    private int id_professeur;
    private String nom;
    private String prenom;
    private int vehiculeCV;

    public SearchableProfesseur() {
        this.id_professeur = id_professeur;
        this.nom = nom;
        this.prenom = prenom;
        this.vehiculeCV = vehiculeCV;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public Integer getVehiculeCV() {
        return vehiculeCV;
    }

}
