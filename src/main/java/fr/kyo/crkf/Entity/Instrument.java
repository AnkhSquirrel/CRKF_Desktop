package fr.kyo.crkf.Entity;

public class Instrument {
    private int id;
    private String nom;
    private TypeInstrument typeInstrument;

    public Instrument(int id,String nom, TypeInstrument typeInstrument) {
        this.id = id;
        this.nom = nom;
        this.typeInstrument = typeInstrument;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public TypeInstrument getTypeInstrument() {
        return typeInstrument;
    }
    public void setTypeInstrument(TypeInstrument typeInstrument) {
        this.typeInstrument = typeInstrument;
    }
}
