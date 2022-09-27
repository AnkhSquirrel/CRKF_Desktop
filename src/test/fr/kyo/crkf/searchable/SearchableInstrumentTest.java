package fr.kyo.crkf.searchable;

import fr.kyo.crkf.entity.Classification;
import fr.kyo.crkf.entity.Famille;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchableInstrumentTest {

    @Test
    void getNom() {
        SearchableInstrument searchableInstrument = new SearchableInstrument();
        assertEquals("", searchableInstrument.getNom());
    }

    @Test
    void setNom() {
        SearchableInstrument searchableInstrument = new SearchableInstrument();
        searchableInstrument.setNom("nom_test");
        assertEquals("nom_test", searchableInstrument.getNom());
    }

    @Test
    void getFamilleId() {
        SearchableInstrument searchableInstrument = new SearchableInstrument();
        assertEquals(0, searchableInstrument.getFamilleId());
    }

    @Test
    void setFamille() {
        SearchableInstrument searchableInstrument = new SearchableInstrument();
        assertNull(searchableInstrument.getFamille());
        searchableInstrument.setFamille(new Famille(1, "famille_test", 0));
        assertEquals(1, searchableInstrument.getFamilleId());
    }

    @Test
    void setClassification() {
        SearchableInstrument searchableInstrument = new SearchableInstrument();
        assertNull(searchableInstrument.getClassification());
        assertEquals(0, searchableInstrument.getClassificationId());
        searchableInstrument.setClassification(new Classification(1, "classification_test"));
        assertEquals(1, searchableInstrument.getClassificationId());
    }

    @Test
    void setClassificationId() {
        SearchableInstrument searchableInstrument = new SearchableInstrument();
        searchableInstrument.setClassification(new Classification(1, "classification_test"));
        assertNotEquals(2, searchableInstrument.getClassificationId());
        searchableInstrument.setClassificationId(2);
        assertEquals(2, searchableInstrument.getClassificationId());
    }

    @Test
    void getClassificationId() {
        SearchableInstrument searchableInstrument = new SearchableInstrument();
        assertEquals(0, searchableInstrument.getClassificationId());
        searchableInstrument.setClassification(new Classification(1, "classification_test"));
        assertEquals(1, searchableInstrument.getClassificationId());
    }

    @Test
    void getNomStringProperty() {
        SearchableInstrument searchableInstrument = new SearchableInstrument();
        assertEquals(SimpleStringProperty.class, searchableInstrument.getNomStringProperty().getClass());
        assertEquals("", searchableInstrument.getNomStringProperty().getValue());
    }

    @Test
    void getClassification() {
        SearchableInstrument searchableInstrument = new SearchableInstrument();
        assertEquals(SimpleStringProperty.class, searchableInstrument.getNomStringProperty().getClass());
        assertEquals("", searchableInstrument.getNomStringProperty().getValue());
    }
}