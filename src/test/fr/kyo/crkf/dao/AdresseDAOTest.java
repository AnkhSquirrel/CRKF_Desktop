package fr.kyo.crkf.dao;

import fr.kyo.crkf.Entity.Adresse;
import fr.kyo.crkf.Entity.Departement;
import fr.kyo.crkf.Entity.Ville;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdresseDAOTest {

    @Test
    void getByID() {
        assertTrue(DAOFactory.getAdresseDAO().getByID(2) != null);
    }

    @Test
    void getAll() {
        ArrayList<Adresse> adresses = new ArrayList<>();
        assertEquals(adresses.getClass(), DAOFactory.getAdresseDAO().getAll(1).getClass());
    }

    @Test
    void insert_update_delete() {
        Adresse adresse = new Adresse(0,"Insert_Adresse",new Ville(1,"yes",0,0,new Departement(0,"","")));
        int id = DAOFactory.getAdresseDAO().insert(adresse);

        assertTrue(id != 0);

        adresse.setId_adresse(id);
        adresse.setAdresse("update_adresse");

        assertTrue(DAOFactory.getAdresseDAO().update(adresse));

        assertTrue(DAOFactory.getAdresseDAO().delete(adresse));
    }
}