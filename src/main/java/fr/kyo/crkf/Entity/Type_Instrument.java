package fr.kyo.crkf.Entity;

public class Type_Instrument {
    private String type;
    private Sous_Type_Instrument sousTypeInstrument;

    public Type_Instrument(String type, Sous_Type_Instrument sousTypeInstrument) {
        this.type = type;
        this.sousTypeInstrument = sousTypeInstrument;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Sous_Type_Instrument getSousTypeInstrument() {
        return sousTypeInstrument;
    }
    public void setSousTypeInstrument(Sous_Type_Instrument sousTypeInstrument) {
        this.sousTypeInstrument = sousTypeInstrument;
    }
}
