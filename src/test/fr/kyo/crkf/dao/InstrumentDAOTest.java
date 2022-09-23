package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Classification;
import fr.kyo.crkf.entity.Famille;
import fr.kyo.crkf.entity.Instrument;
import fr.kyo.crkf.searchable.SearchableInstrument;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InstrumentDAOTest {

    @Test
    void getByID() {
        assertTrue(DAOFactory.getInstrumentDAO().getByID(1) != null);
    }

    @Test
    void getAll() {
        ArrayList<Instrument> instruments = new ArrayList<>();
        assertEquals(instruments.getClass(), DAOFactory.getInstrumentDAO().getAll(1).getClass());
    }

    @Test
    void getLike() {
        SearchableInstrument searchableInstrument = new SearchableInstrument();
        ArrayList<Instrument> instruments = new ArrayList<>();
        assertEquals(instruments.getClass(), DAOFactory.getInstrumentDAO().getLike(searchableInstrument).getClass());
    }

    @Test
    void insert_update_delete() {
        Instrument instrument = new Instrument(0, "Insert_instrument");
        instrument.addFamille(new Famille(1,"",new Classification(0,"")));

        int id = DAOFactory.getInstrumentDAO().insert(instrument);

        assertTrue(id != 0);

        instrument.setId_instrument(id);
        instrument.setNom("Update_Instrument");

        assertTrue(DAOFactory.getInstrumentDAO().update(instrument));
        assertTrue(DAOFactory.getInstrumentDAO().delete(instrument));
    }
}