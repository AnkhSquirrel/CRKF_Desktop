package fr.kyo.crkf.Entity;

import fr.kyo.crkf.dao.DAOFactory;

public class Diplome {
    private int cycle;
    private int instrument;

    public Diplome(int cycle, int instrument) {
        this.cycle = cycle;
        this.instrument = instrument;
    }
    public Cycle getCycle() {
        return DAOFactory.getCycleDAO().getByID(cycle);
    }
    public void setCycle(Cycle cycle) {
        this.cycle = cycle.getId_cycle();
    }
    public Instrument getInstrument() {
        return DAOFactory.getInstrumentDAO().getByID(instrument);
    }
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument.getId_instrument();
    }
}
