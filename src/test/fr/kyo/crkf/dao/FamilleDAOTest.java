package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Classification;
import fr.kyo.crkf.entity.Famille;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FamilleDAOTest {

    @Test
    void getByID() {
        assertTrue(DAOFactory.getFamilleDAO().getByID(1) != null);
    }

    @Test
    void getAll() {
        ArrayList<Famille> familles = new ArrayList<>();
        assertEquals(familles.getClass(),DAOFactory.getFamilleDAO().getAll(1).getClass());
    }

    @Test
    void insert() {
        Famille famille = new Famille(0,"Insert_Famille", new Classification(1,""));
        int id = DAOFactory.getFamilleDAO().insert(famille);

        assertTrue(id != 0);

        famille.setId_famille(id);
        famille.setfamille("Update_Famille");

        assertTrue(DAOFactory.getFamilleDAO().update(famille));

        assertTrue(DAOFactory.getFamilleDAO().delete(famille));
    }
}