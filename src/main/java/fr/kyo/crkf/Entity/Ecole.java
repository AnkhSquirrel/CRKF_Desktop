package fr.kyo.crkf.Entity;

public class Ecole {
    private Adresse adresse;

    public Ecole(Adresse adresse) {
        this.adresse = adresse;
    }
    public Adresse getAdresse() {
        return adresse;
    }
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
}
