package fr.kyo.crkf.Entity;

public class Ville {
    private String ville;
    private Departement departement;

    public Ville(String ville, Departement departement) {
        this.ville = ville;
        this.departement = departement;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public Departement getDepartement() {
        return departement;
    }
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
