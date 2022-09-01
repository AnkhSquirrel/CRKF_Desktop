package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Departement;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DepartementDAOTest {
    @Test
    void getByID() {
        assertEquals(Departement.class, DAOFactory.getDepartementDAO().getByID(1).getClass());
    }

    @Test
    void getAll() {
        ArrayList<Departement> departements = new ArrayList<>();

        assertEquals(departements.getClass(), DAOFactory.getDepartementDAO().getAll(1).getClass());
    }

    @Test
    void insert_update_delete() {
        Departement departement = new Departement(0, "108","Test_Insert");
        int id = DAOFactory.getDepartementDAO().insert(departement);

        assertTrue( id != 0);

        departement.setId_departement(id);
        departement.setDepartement("Update");

        assertTrue(DAOFactory.getDepartementDAO().update(departement));

        assertTrue(DAOFactory.getDepartementDAO().delete(departement));
    }
}