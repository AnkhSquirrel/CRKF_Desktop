package fr.kyo.crkf.Entity;

public class Ecole {
    private int id_ecole;
    private Adresse adresse;

    public Ecole(int id_ecole, Adresse adresse) {
        this.id_ecole = id_ecole;
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
}
