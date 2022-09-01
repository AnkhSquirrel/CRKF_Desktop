package fr.kyo.crkf.Searchable;

public class SearchableProfesseur {
    private int id_professeur;
    private String nomEtPrenom;
    private int vehiculeCV;

    public SearchableProfesseur() {
        this.id_professeur = id_professeur;
        this.nomEtPrenom = nomEtPrenom;
        this.vehiculeCV = vehiculeCV;
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

}
