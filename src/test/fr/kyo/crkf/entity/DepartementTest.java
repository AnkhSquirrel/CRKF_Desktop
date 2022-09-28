package fr.kyo.crkf.entity;


import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartementTest {

    @Test
    void getDepartementId() {
        Departement departement = new Departement(1, "1", "departement_test");
        assertEquals(1, departement.getDepartementId());
    }

    @Test
    void getDepartementNumero() {
        Departement departement = new Departement(1, "1", "departement_test");
        assertEquals("1", departement.getDepartementNumero());
    }

    @Test
    void setDepartementNumero() {
        Departement departement = new Departement(1, "1", "departement_test");
        departement.setDepartementNumero("2");
        assertEquals("2", departement.getDepartementNumero());
    }

    @Test
    void getDepartementLibelle() {
        Departement departement = new Departement(1, "1", "departement_test");
        assertEquals("departement_test", departement.getDepartementLibelle());
    }

    @Test
    void setDepartementLibelle() {
        Departement departement = new Departement(1, "1", "departement_test");
        departement.setDepartementLibelle("departement_nouveau_libelle");
        assertEquals("departement_nouveau_libelle", departement.getDepartementLibelle());
    }

    @Test
    void getDepartementStringProperty() {
        Departement departement = new Departement(1, "1", "departement_test");
        assertEquals("departement_test", departement.getDepartementStringProperty().getValue());
        assertSame(SimpleStringProperty.class, departement.getDepartementStringProperty().getClass());
    }

    @Test
    void getNumDepartementString() {
        Departement departement = new Departement(1, "1", "departement_test");
        assertEquals("1", departement.getNumDepartementString().getValue());
        assertSame(SimpleStringProperty.class, departement.getNumDepartementString().getClass());
    }

    @Test
    void testToString() {
        Departement departement = new Departement(1, "1", "departement_test");
        assertEquals("1 - departement_test", departement.toString());
        departement = new Departement(0, "1", "departement_test");
        assertEquals("departement_test", departement.toString());
    }
}