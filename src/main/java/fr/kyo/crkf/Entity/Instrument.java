package fr.kyo.crkf.Entity;

public class Instrument {
    private String nom;
    private Type_Instrument typeInstrument;

    public Instrument(String nom, Type_Instrument typeInstrument) {
        this.nom = nom;
        this.typeInstrument = typeInstrument;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Type_Instrument getTypeInstrument() {
        return typeInstrument;
    }
    public void setTypeInstrument(Type_Instrument typeInstrument) {
        this.typeInstrument = typeInstrument;
    }
}
