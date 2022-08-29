package fr.kyo.crkf.Entity;

public class Diplome {
    private int id_diplome;
    private String libelle;

    public Diplome(int id_diplome, String libelle) {
        this.id_diplome = id_diplome;
        this.libelle = libelle;
    }
    public int getId_diplome() {
        return id_diplome;
    }
    public void setId_diplome(int id_diplome) {
        this.id_diplome = id_diplome;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
