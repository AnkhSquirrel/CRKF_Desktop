package fr.kyo.crkf.searchable;

public class SearchableFamille {

    private String nom;
    private int classification;

    public SearchableFamille(){
        nom = "";
        classification = 0;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }
}
