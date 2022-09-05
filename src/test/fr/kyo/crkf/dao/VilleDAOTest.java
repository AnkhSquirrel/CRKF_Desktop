package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VilleDAOTest {

    @Test
    void getByID() {
        assertTrue(DAOFactory.getVilleDAO().getByID(1) != null);
    }

    @Test
    void getAll() {
        ArrayList<Ville> villes = new ArrayList<>();
        assertEquals(villes.getClass(),DAOFactory.getVilleDAO().getAll(1).getClass());
    }

    @Test
    void insert_update_delete() {
        Ville ville = new Ville(0,"Ville_Test",0,0,new Departement(1,"",""));
        int id = DAOFactory.getVilleDAO().insert(ville);

        assertTrue(id != 0);

        ville.setId_ville(id);
        ville.setVille("Ville_test 2");

        assertTrue(DAOFactory.getVilleDAO().update(ville));

        assertTrue(DAOFactory.getVilleDAO().delete(ville));
    }
}