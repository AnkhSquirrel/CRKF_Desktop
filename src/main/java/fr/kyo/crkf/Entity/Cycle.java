package fr.kyo.crkf.Entity;

public class Cycle {
    private int id_cycle;
    private String libelle;
    private int cycle;

    public Cycle(int id_cycle, String libelle, int cycle) {
        this.id_cycle = id_cycle;
        this.libelle = libelle;
        this.cycle = cycle;
    }

    public int getId_cycle() {
        return id_cycle;
    }

    public void setId_cycle(int id_cycle) {
        this.id_cycle = id_cycle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }
}
