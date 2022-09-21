package fr.kyo.crkf.dao;

import fr.kyo.crkf.entity.Adresse;
import fr.kyo.crkf.entity.Departement;
import fr.kyo.crkf.entity.Ecole;
import fr.kyo.crkf.entity.Ville;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EcoleDAOTest {

    @Test
    void getByID() {
        assertTrue(DAOFactory.getEcoleDAO().getByID(1) != null);
    }

    @Test
    void getAll() {
        ArrayList<Ecole> ecoles = new ArrayList<>();
        assertEquals(ecoles.getClass(), DAOFactory.getEcoleDAO().getAll(1).getClass());
    }

    @Test
    void insert_update_delete() {
        Ecole ecole = new Ecole(0,"Insert_Ecole",new Adresse(2,"Insert_Adresse",new Ville(1,"yes",0,0,new Departement(0,"",""))));
        int id = DAOFactory.getEcoleDAO().insert(ecole);

        assertTrue(id != 0);

        ecole.setId_ecole(id);
        ecole.setNom("Update_Ecole");

        assertTrue(DAOFactory.getEcoleDAO().update(ecole));

        assertTrue(DAOFactory.getEcoleDAO().delete(ecole));
    }
}