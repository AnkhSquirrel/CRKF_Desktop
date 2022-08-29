package fr.kyo.crkf.Entity;

public class Adresse {
    private String adresse;
    private Ville ville;

    public Adresse(String adresse, Ville ville) {
        this.adresse = adresse;
        this.ville = ville;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public Ville getVille() {
        return ville;
    }
    public void setVille(Ville ville) {
        this.ville = ville;
    }
}
