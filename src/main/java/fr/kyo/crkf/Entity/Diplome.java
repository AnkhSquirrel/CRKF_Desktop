package fr.kyo.crkf.Entity;

import javafx.beans.value.ObservableValue;

public class Diplome {
    private Cycle cycle;
    private Instrument instrument;

    public Diplome(Cycle cycle, Instrument instrument) {
        this.cycle = cycle;
        this.instrument = instrument;
    }
    public Cycle getCycle() {
        return cycle;
    }
    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }
    public Instrument getInstrument() {
        return instrument;
    }
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
