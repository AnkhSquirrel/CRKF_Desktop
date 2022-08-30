package fr.kyo.crkf.Entity;

public class Ecole {
    private int id_ecole;
    private String Nom;
    private Adresse adresse;

    public Ecole(int id_ecole, String Nom ,Adresse adresse) {
        this.id_ecole = id_ecole;
        this.Nom = Nom;
        this.adresse = adresse;
    }
    public int getId_ecole() {
        return id_ecole;
    }
    public void setId_ecole(int id_ecole) {
        this.id_ecole = id_ecole;
    }
    public Adresse getAdresse() {
        return adresse;
    }
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
    public String getNom() {
        return Nom;
    }
    public void setNom(String nom) {
        Nom = nom;
    }
}
